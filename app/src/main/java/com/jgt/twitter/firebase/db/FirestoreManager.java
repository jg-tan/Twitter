package com.jgt.twitter.firebase.db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jgt.twitter.firebase.db.entity.Tweet;
import com.jgt.twitter.firebase.db.entity.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import timber.log.Timber;

public class FirestoreManager implements ChildEventListener {

    private static FirestoreManager instance;

    private final DatabaseReference mRef;

    private FirestoreListener listener;

    private User currentUser;

    private FirestoreManager() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
    }

    public static FirestoreManager getInstance() {
        if (null == instance) {
            synchronized (FirestoreManager.class) {
                instance = new FirestoreManager();
            }
        }
        return instance;
    }

    public void createUser(User user, String uid) {
        Timber.d("Add user to db");
        mRef.child("users").child(uid).setValue(user);
    }

    public void init(String uid, FirestoreListener listener) {
        Timber.d("Init FirestoreManager");
        this.listener = listener;
        mRef.child("users").child(uid).get().addOnCompleteListener(this::onGetCompleted);
        mRef.child("users").child(uid).child("tweets").removeEventListener(this);
        mRef.child("users").child(uid).child("tweets").addChildEventListener(this);
    }

    public void addTweet(Tweet tweet) {
        mRef.child("users").child(currentUser.getUid()).child("tweets").push().setValue(tweet);
    }

    public void deleteTweet(Tweet tweet) {
        if (null != tweet && null != tweet.getTweetId()) {
            mRef.child("users").child(currentUser.getUid()).child("tweets").child(tweet.getTweetId()).removeValue();
        }
    }

    public void editTweet(String tweetId, String body) {
        mRef.child("users").child(currentUser.getUid()).child("tweets").child(tweetId).child("body").setValue(body);
    }

    public String getCurrentUsername() {
        return null != currentUser ? currentUser.getUsername() : null;
    }

    private void onGetCompleted(Task<DataSnapshot> task) {
        if (task.isSuccessful()) {
            DataSnapshot result = task.getResult();
            if (null != result) {
                this.currentUser = result.getValue(User.class);
                this.currentUser.setUid(result.getKey());
                if (2 == result.getChildrenCount()) {
                    //No Tweets yet, send call back that tweets has been loaded
                    listener.onTweetLoaded();
                }
                Timber.d("User loaded: " + currentUser);
                listener.onUserLoaded();
            } else {
                Timber.e("Failed to load user. user == null");
                listener.onFailure("Failed to load user.");
            }
        } else {
            Timber.e(task.getException());
            listener.onFailure("Failed to load user.");
        }
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Tweet tweet = snapshot.getValue(Tweet.class);
        tweet.setTweetId(snapshot.getKey());
        Timber.d("onChildAdded (Tweet): " + tweet);
        listener.onTweetAdded(tweet);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
        String key = snapshot.getKey();
        Timber.d("onChildAdded (Tweet): " + key);
        listener.onTweetDeleted(snapshot.getKey());
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}