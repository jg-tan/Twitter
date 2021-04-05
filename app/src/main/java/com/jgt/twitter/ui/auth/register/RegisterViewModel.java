package com.jgt.twitter.ui.auth.register;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.jgt.twitter.firebase.auth.FirebaseAuthListener;
import com.jgt.twitter.firebase.auth.FirebaseAuthManager;
import com.jgt.twitter.firebase.db.FirestoreManager;
import com.jgt.twitter.firebase.db.entity.User;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class RegisterViewModel extends ViewModel implements FirebaseAuthListener {

    private final MutableLiveData<Boolean> onRegister = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();

    private final FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
    private final FirestoreManager firestoreManager = FirestoreManager.getInstance();

    public LiveData<Boolean> getOnRegister() {
        return onRegister;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void register(FragmentActivity activity, String username, String email, String password) {
        if (TextUtils.isEmpty(username)) {
            toastMessage.postValue("Username cannot be empty.");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            toastMessage.postValue("Email cannot be empty.");

            return;
        }

        if (TextUtils.isEmpty(password)) {
            toastMessage.postValue("Password cannot be empty.");
            return;
        }

        if (password.length() < 6) {
            toastMessage.postValue("Password length must not be less than 6 characters.");
            return;
        }

        authManager.register(activity, this, email, password);
    }

    @Override
    public void onSuccess() {
        Timber.d("Register success!");
        onRegister.postValue(true);
    }

    @Override
    public void onFailure() {
        Timber.e("Register failed!");
        toastMessage.postValue("Register failed!");
        onRegister.postValue(false);
    }

    public void addUserToDb(String username, String email) {
        FirebaseUser firebaseUser = authManager.getCurrentUser();
        String uid = firebaseUser.getUid();
        User user = new User(username, email);
        firestoreManager.createUser(user, uid);
    }
}