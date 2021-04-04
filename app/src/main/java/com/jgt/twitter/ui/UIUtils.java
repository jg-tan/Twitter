package com.jgt.twitter.ui;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class UIUtils {
    private static final String TAG = UIUtils.class.getSimpleName();

    public static void setUpToolbar(AppCompatActivity appCompatActivity, boolean displayBack,
                                    String title) {
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(displayBack);
        actionBar.setTitle(title);
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}