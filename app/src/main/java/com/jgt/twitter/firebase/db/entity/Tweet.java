package com.jgt.twitter.firebase.db.entity;

public class Tweet {

    private String body;
    private long timestamp;
    private String tweetId;

    public Tweet() {
    }

    public Tweet(String body, long timestamp) {
        this.body = body;
        this.timestamp = timestamp;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetId() {
        return this.tweetId;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "body='" + body + '\'' +
                ", timestamp=" + timestamp +
                ", tweetId='" + tweetId + '\'' +
                '}';
    }
}