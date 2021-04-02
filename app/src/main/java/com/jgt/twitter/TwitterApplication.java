package com.jgt.twitter;

import android.app.Application;

import timber.log.Timber;

public class TwitterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}