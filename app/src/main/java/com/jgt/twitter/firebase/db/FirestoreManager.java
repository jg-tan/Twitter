package com.jgt.twitter.firebase.db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jgt.twitter.firebase.db.entity.FirestoreListener;
import com.jgt.twitter.firebase.db.entity.Tweet;
import com.jgt.twitter.firebase.db.entity.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import timber.log.Timber;

public class FirestoreManager implements ChildEventListener {

    private static FirestoreManager instance;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private FirestoreListener listener;

    private User currentUser;

    private FirestoreManager() {
        mDatabase = FirebaseDatabase.getInstance();
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
        mRef.child("users").child(uid).setValue(user);
    }

    public void init(String uid, FirestoreListener listener) {
        this.listener = listener;
        mRef.child("users").child(uid).get().addOnCompleteListener(this::onGetCompleted);
        mRef.child("users").child(uid).child("tweets").get().addOnCompleteListener(this::onGetTweetsCompleted);
        mRef.child("users").child(uid).child("tweets").addChildEventListener(this);
    }

    public void addTweet(String uid, Tweet tweet) {
        mRef.child("users").child(uid).child("tweets").push().setValue(tweet);
    }

    public void deleteTweet(String uid, Tweet tweet) {
        if (null != tweet && null != tweet.getTweetId()) {
            mRef.child("users").child(uid).child("tweets").child(tweet.getTweetId()).removeValue();
        }
    }

    public String getCurrentUsername() {
        return null != currentUser ? currentUser.getUsername() : null;
    }

    private void onGetCompleted(Task<DataSnapshot> task) {
        if (task.isSuccessful()) {
            DataSnapshot result = task.getResult();
            this.currentUser = result.getValue(User.class);
            listener.onUserLoaded();
        } else {
            Timber.e(task.getException());
            listener.onFailure("Failed to load user.");
        }
    }

    private void onGetTweetsCompleted(Task<DataSnapshot> task) {
        if (task.isSuccessful()) {
            DataSnapshot result = task.getResult();
            List<Tweet> tweets = new ArrayList<>();
            Iterable<DataSnapshot> children = result.getChildren();

            for (DataSnapshot child : children) {
                Tweet tweet = child.getValue(Tweet.class);
                if (null != tweet) {
                    tweet.setTweetId(child.getKey());
                    tweets.add(tweet);
                }
            }

            listener.onTweetsRetrieved(tweets);
        } else {
            Timber.e(task.getException());
            listener.onFailure("Failed to retrieve tweets.");
        }
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Tweet tweet = snapshot.getValue(Tweet.class);
        tweet.setTweetId(snapshot.getKey());
        listener.onTweetAdded(tweet);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
        listener.onTweetDeleted(snapshot.getKey());
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    public void cleanUp() {
        instance = null;
    }
}