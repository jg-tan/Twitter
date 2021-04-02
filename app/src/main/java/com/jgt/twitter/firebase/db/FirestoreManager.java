package com.jgt.twitter.firebase.db;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreManager {

    private static FirestoreManager instance;

    private FirebaseFirestore db;

    private FirestoreManager() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirestoreManager getInstance() {
        if (null == instance) {
            synchronized (FirestoreManager.class) {
                instance = new FirestoreManager();
            }
        }
        return instance;
    }
}