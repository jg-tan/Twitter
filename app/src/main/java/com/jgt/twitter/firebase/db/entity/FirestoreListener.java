package com.jgt.twitter.firebase.db.entity;

import java.util.List;

public interface FirestoreListener {

    void onTweetsRetrieved(List<Tweet> tweets);

    void onUserLoaded();

    void onFailure(String message);

    void onTweetAdded(Tweet tweet);

    void onTweetDeleted(String key);
}
