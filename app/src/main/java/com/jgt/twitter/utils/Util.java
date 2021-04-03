package com.jgt.twitter.utils;

import com.jgt.twitter.firebase.auth.FirebaseAuthManager;
import com.jgt.twitter.firebase.db.FirestoreManager;

import java.text.SimpleDateFormat;

public class Util {
    public static String toDate(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        return dateFormat.format(timeStamp);
    }

    public static void cleanUpFirebase() {
        FirebaseAuthManager.getInstance().cleanUp();
        FirestoreManager.getInstance().cleanUp();
    }
}