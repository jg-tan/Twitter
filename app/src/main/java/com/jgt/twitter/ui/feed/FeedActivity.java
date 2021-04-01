package com.jgt.twitter.ui.feed;

import android.os.Bundle;

import com.jgt.twitter.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FeedActivity extends AppCompatActivity {
    private static final String TAG = FeedActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
    }
}