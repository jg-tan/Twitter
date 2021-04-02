package com.jgt.twitter.firebase.db.entity;

public class Tweet {

    private String username;
    private String body;
    private long timestamp;

    public Tweet(String username, String body, long timestamp) {
        this.username = username;
        this.body = body;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getBody() {
        return body;
    }

    public long getTimestamp() {
        return timestamp;
    }
}