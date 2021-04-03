package com.jgt.twitter.firebase.auth;

import android.app.Activity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jgt.twitter.firebase.db.FirestoreManager;

import timber.log.Timber;

public class FirebaseAuthManager {

    private static FirebaseAuthManager instance;

    private FirebaseAuth mAuth;
    private FirebaseAuthListener listener;
    private FirebaseUser mUser;

    private FirebaseAuthManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseAuthManager getInstance() {
        if (null == instance) {
            synchronized (FirebaseAuthManager.class) {
                instance = new FirebaseAuthManager();
            }
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return null != mAuth.getCurrentUser();
    }

    public void signIn(Activity activity, FirebaseAuthListener listener, String email, String password) {
        this.listener = listener;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, this::onSignInComplete);
    }

    public void register(Activity activity, FirebaseAuthListener listener, String email, String password) {
        this.listener = listener;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, this::onRegisterComplete);
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void signOut() {
        mAuth.signOut();
        if (isLoggedIn()) {
            cleanUp();
        }
    }

    public void loadCurrentUser() {
        mUser = getCurrentUser();
    }

    public String getUserId() {
        return null != mUser ? mUser.getUid() : null;
    }

    private void onSignInComplete(Task<AuthResult> authResultTask) {
        if (authResultTask.isSuccessful()) {
            Timber.d("Sign in success");
            listener.onSuccess();
        } else {
            Timber.e("Sign in failed: " + authResultTask.getException());
            listener.onFailure();
        }
    }

    private void onRegisterComplete(Task<AuthResult> authResultTask) {
        if (authResultTask.isSuccessful()) {
            Timber.d("Register success");
            listener.onSuccess();
        } else {
            Timber.e("Register failed: " + authResultTask.getException());
            listener.onFailure();
        }
    }

    private void cleanUp() {
        mUser = null;
        FirestoreManager.getInstance().cleanUp();
        instance = null;
    }
}