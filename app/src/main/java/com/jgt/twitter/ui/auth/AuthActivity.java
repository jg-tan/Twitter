package com.jgt.twitter.ui.auth;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.jgt.twitter.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {
    private static final String TAG = AuthActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}