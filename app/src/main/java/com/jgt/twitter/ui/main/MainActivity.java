package com.jgt.twitter.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.jgt.twitter.R;
import com.jgt.twitter.ui.auth.AuthActivity;
import com.jgt.twitter.ui.feed.FeedActivity;
import com.jgt.twitter.utils.SharedPrefUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isLoggedIn = SharedPrefUtils.get().isLoggedIn();
        Timber.d("isLoggedIn: " + isLoggedIn);

        if (isLoggedIn) {
            startFeedActivity();
        } else {
            startAuthActivity();
        }
    }

    private void startAuthActivity() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    private void startFeedActivity() {
        startActivity(new Intent(this, FeedActivity.class));
        finish();
    }
}