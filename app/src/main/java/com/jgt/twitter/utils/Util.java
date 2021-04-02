package com.jgt.twitter.utils;

import com.jgt.twitter.firebase.db.entity.Tweet;

import java.util.LinkedList;
import java.util.List;

public class Util {

    //Temp
    public static List<Tweet> getSampleTweetList(int size) {
        List<Tweet> tweetList = new LinkedList<>();

        for (int i = 1; i <= size; i++) {
            tweetList.add(new Tweet("body " + i, i * 11111));
        }

        return tweetList;
    }
}