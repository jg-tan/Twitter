package com.jgt.twitter.ui.auth.login;

import android.text.TextUtils;

import com.jgt.twitter.firebase.auth.FirebaseAuthListener;
import com.jgt.twitter.firebase.auth.FirebaseAuthManager;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class LoginViewModel extends ViewModel implements FirebaseAuthListener {

    private MutableLiveData<Boolean> onLogin = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();

    private FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();

    public LiveData<Boolean> getOnLogin() {
        return onLogin;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void login(FragmentActivity activity, String email, String password) {
        //TODO add regex for email
        if (TextUtils.isEmpty(email)) {
            toastMessage.postValue("Email is empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            toastMessage.postValue("Password is empty");
            return;
        }

        authManager.signIn(activity, this, email, password);
    }

    @Override
    public void onSuccess() {
        Timber.d("Logged in!");
        onLogin.postValue(true);
        toastMessage.postValue("Logged in!");
    }

    @Override
    public void onFailure() {
        Timber.d("Log in failed!");
        onLogin.postValue(false);
        toastMessage.postValue("Log in failed!");
    }
}