package com.jgt.twitter;

import android.app.Application;

import timber.log.Timber;

public class TwitterApplication extends Application {
    private static final String TAG = TwitterApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}