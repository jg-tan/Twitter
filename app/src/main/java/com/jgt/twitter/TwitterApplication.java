package com.jgt.twitter;

import android.app.Application;

import com.jgt.twitter.firebase.auth.FirebaseAuthManager;
import com.jgt.twitter.utils.Util;

import timber.log.Timber;

public class TwitterApplication extends Application {

    private static TwitterApplication instance;

    public TwitterApplication() {
        instance = this;
    }

    public static TwitterApplication getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Util.cleanUpFirebase();
    }
}