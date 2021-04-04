package com.jgt.twitter.ui.feed.feed;

import com.jgt.twitter.firebase.auth.FirebaseAuthManager;
import com.jgt.twitter.firebase.db.FirestoreListener;
import com.jgt.twitter.firebase.db.FirestoreManager;
import com.jgt.twitter.firebase.db.entity.Tweet;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeedViewModel extends ViewModel implements FirestoreListener {

    private final MutableLiveData<Boolean> onSignOut = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<List<Tweet>> obsTweetList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasLoaded = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingError = new MutableLiveData<>();

    private final FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
    private final FirestoreManager firestoreManager = FirestoreManager.getInstance();

    private boolean isUserLoaded = false;
    private boolean isTweetLoaded = false;
    private int retry = 0;

    private static final int MAX_RETRIES = 3;

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

    public LiveData<Boolean> getHasLoaded() {
        return hasLoaded;
    }

    public LiveData<Boolean> isLoadingError() {
        return isLoadingError;
    }

    public void loadCurrentUser() {
        if (MAX_RETRIES > retry) {
            authManager.loadCurrentUser();
            firestoreManager.init(authManager.getUserId(), this);
            obsTweetList.postValue(new ArrayList<>());
        } else {
            isLoadingError.postValue(true);
            retry = 0;
        }
        retry++;
    }

    public void signOut() {
        authManager.signOut();
        boolean isLoggedOff = !authManager.isLoggedIn();
        onSignOut.postValue(isLoggedOff);
    }

    public void addTweet(String body, long timestamp) {
        Tweet tweet = new Tweet(body, timestamp);
        firestoreManager.addTweet(tweet);
    }

    public void deleteTweet(Tweet tweet) {
        firestoreManager.deleteTweet(tweet);
    }

    @Override
    public void onFailure(String message) {
        hasLoaded.postValue(false);
    }

    @Override
    public void onTweetAdded(Tweet tweet) {
        List<Tweet> tweets = obsTweetList.getValue();
        tweets.add(0, tweet);
        obsTweetList.postValue(tweets);
        if (!isLoaded()) {
            isTweetLoaded = true;
            checkIfLoaded();
        }
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
    public void onUserLoaded() {
        username.postValue(firestoreManager.getCurrentUsername());
        isUserLoaded = true;
        checkIfLoaded();
    }

    @Override
    public void onTweetLoaded() {
        isTweetLoaded = true;
        checkIfLoaded();
    }

    private void checkIfLoaded() {
        if (isLoaded()) {
            hasLoaded.postValue(true);
        }
    }

    private boolean isLoaded() {
        return isUserLoaded && isTweetLoaded;
    }
}