package com.jgt.twitter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jgt.twitter.TwitterApplication;

public class SharedPrefUtils {

    private static SharedPrefUtils instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private final String SHARED_PREF = "TwitterSharedPref";
    private final String PREF_EMAIL = "prefEmail";
    private final String PREF_PASSWORD = "prefPassword";

    private SharedPrefUtils() {
        pref = TwitterApplication.getContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static SharedPrefUtils get() {
        if (null == instance) {
            synchronized (SharedPrefUtils.class) {
                instance = new SharedPrefUtils();
            }
        }
        return instance;
    }

    public String getEmail() {
        return pref.getString(PREF_EMAIL, "");
    }

    public void setEmail(String email) {
        editor.putString(PREF_EMAIL, email);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PREF_PASSWORD, "");
    }

    public void setPassword(String password) {
        editor.putString(PREF_PASSWORD, password);
        editor.commit();
    }

    public void clearCredentials() {
        saveCredentials("", "");
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getEmail()) && !TextUtils.isEmpty(getPassword());
    }

    public void saveCredentials(String email, String password) {
        setEmail(email);
        setPassword(password);
    }
}