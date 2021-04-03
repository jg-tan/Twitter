package com.jgt.twitter.firebase.db.entity;

public interface FirestoreListener {
    void onUserLoaded();

    void onFailure(String message);

    void onTweetAdded(Tweet tweet);

    void onTweetDeleted(String key);
}
