package com.jgt.twitter.utils;

import com.google.gson.Gson;
import com.jgt.twitter.firebase.db.entity.Tweet;

import java.text.SimpleDateFormat;

public class Util {
    public static String toDate(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        return dateFormat.format(timeStamp);
    }

    public static String toJson(Tweet tweet) {
        Gson gson = new Gson();
        return gson.toJson(tweet);
    }

    public static Tweet toTweet(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Tweet.class);
    }
}