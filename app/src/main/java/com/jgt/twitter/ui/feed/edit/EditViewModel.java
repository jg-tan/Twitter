package com.jgt.twitter.ui.feed.edit;

import com.jgt.twitter.firebase.db.FirestoreManager;
import com.jgt.twitter.firebase.db.entity.Tweet;
import com.jgt.twitter.utils.Util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditViewModel extends ViewModel {

    public final MutableLiveData<Boolean> isTweetLoaded = new MutableLiveData<>();

    private final FirestoreManager firestoreManager = FirestoreManager.getInstance();

    public Tweet tweet;

    public LiveData<Boolean> getIsTweetLoaded() {
        return isTweetLoaded;
    }

    public void loadTweet(Tweet tweet) {
        this.tweet = tweet;
        isTweetLoaded.postValue(true);
    }

    public String getUsername() {
        return firestoreManager.getCurrentUsername();
    }

    public String getTweetBody() {
        return null != tweet ? tweet.getBody() : null;
    }

    public String getTweetDate() {
        return null != tweet ? Util.toDate(tweet.getTimestamp()) : null;
    }

    public void editTweet(String tweetBody) {
        if (null != tweet) {
            firestoreManager.editTweet(tweet.getTweetId(), tweetBody);
        }
    }
}