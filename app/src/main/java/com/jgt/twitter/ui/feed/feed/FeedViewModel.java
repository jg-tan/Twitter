package com.jgt.twitter.ui.feed.feed;

import com.jgt.twitter.firebase.auth.FirebaseAuthManager;
import com.jgt.twitter.firebase.db.FirestoreManager;
import com.jgt.twitter.firebase.db.entity.FirestoreListener;
import com.jgt.twitter.firebase.db.entity.Tweet;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeedViewModel extends ViewModel implements FirestoreListener {

    private MutableLiveData<Boolean> onSignOut = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<List<Tweet>> obsTweetList = new MutableLiveData<>();

    private FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
    private FirestoreManager firestoreManager = FirestoreManager.getInstance();

    private List<Tweet> tweetList = new ArrayList<>();

    public LiveData<Boolean> getOnSignOut() {
        return onSignOut;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public LiveData<List<Tweet>> getObsTweetList() {
        return obsTweetList;
    }

    public void loadCurrentUser() {
        authManager.loadCurrentUser();
        firestoreManager.init(authManager.getUserId(), this);
    }

    public void signOut() {
        authManager.signOut();
        boolean isLoggedOff = !authManager.isLoggedIn();
        onSignOut.postValue(isLoggedOff);
        toastMessage.postValue(isLoggedOff ? "Logged off!" : "Logged off failed!");
    }

    public void addTweet(String body, long timestamp) {
        Tweet tweet = new Tweet(body, timestamp);
        firestoreManager.addTweet(authManager.getUserId(), tweet);
    }

    @Override
    public void onFailure(String message) {
        toastMessage.postValue(message);
    }

    @Override
    public void onTweetAdded(Tweet tweet) {
        tweetList.add(0, tweet);
        obsTweetList.postValue(tweetList);
    }

    @Override
    public void onTweetDeleted(String key) {

    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        obsTweetList.postValue(tweets);
    }

    @Override
    public void onUserLoaded() {
        toastMessage.postValue("User loaded successfully");
        username.postValue(firestoreManager.getCurrentUsername());
    }
}