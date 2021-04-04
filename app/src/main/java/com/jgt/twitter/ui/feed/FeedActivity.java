package com.jgt.twitter.ui.feed;

import android.os.Bundle;

import com.jgt.twitter.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FeedActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        toolbar = findViewById(R.id.tbFeed);
        setSupportActionBar(toolbar);
    }
}