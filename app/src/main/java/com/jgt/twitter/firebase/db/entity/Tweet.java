package com.jgt.twitter.firebase.db.entity;

public class Tweet {

    private String body;
    private long timestamp;

    public Tweet() {
    }

    public Tweet(String body, long timestamp) {
        this.body = body;
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public long getTimestamp() {
        return timestamp;
    }
}