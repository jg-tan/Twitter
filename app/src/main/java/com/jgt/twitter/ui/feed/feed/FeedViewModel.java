package com.jgt.twitter.ui.feed.feed;

import com.jgt.twitter.firebase.auth.FirebaseAuthManager;
import com.jgt.twitter.firebase.db.FirestoreManager;
import com.jgt.twitter.firebase.db.entity.FirestoreListener;
import com.jgt.twitter.firebase.db.entity.Tweet;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class FeedViewModel extends ViewModel implements FirestoreListener {

    private MutableLiveData<Boolean> onSignOut;
    private MutableLiveData<String> toastMessage;
    private MutableLiveData<String> username;
    private MutableLiveData<List<Tweet>> obsTweetList;

    private FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
    private FirestoreManager firestoreManager = FirestoreManager.getInstance();

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

    public void init() {
        onSignOut = new MutableLiveData<>();
        toastMessage = new MutableLiveData<>();
        username = new MutableLiveData<>();
        obsTweetList = new MutableLiveData<>();
    }

    public void cleanUp(LifecycleOwner owner) {
        onSignOut.removeObservers(owner);
        toastMessage.removeObservers(owner);
        username.removeObservers(owner);
        obsTweetList.removeObservers(owner);
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

    public void deleteTweet(Tweet tweet) {
        firestoreManager.deleteTweet(authManager.getUserId(), tweet);
    }

    @Override
    public void onFailure(String message) {
        toastMessage.postValue(message);
    }

    @Override
    public void onTweetAdded(Tweet tweet) {
        List<Tweet> tweets = obsTweetList.getValue();
        if (null == tweets) {
            tweets = new ArrayList<>();
        }
        tweets.add(0, tweet);
        obsTweetList.postValue(tweets);
    }

    @Override
    public void onTweetDeleted(String key) {
        List<Tweet> tweets = obsTweetList.getValue();
        if (null != tweets) {
            tweets.removeIf(tweet -> key.equals(tweet.getTweetId()));
            obsTweetList.postValue(tweets);
        }
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