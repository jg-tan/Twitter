package com.jgt.twitter.ui.auth.register;

import android.text.TextUtils;

import com.jgt.twitter.firebase.auth.FirebaseAuthListener;
import com.jgt.twitter.firebase.auth.FirebaseAuthManager;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class RegisterViewModel extends ViewModel implements FirebaseAuthListener {

    private MutableLiveData<Boolean> onRegister = new MutableLiveData<>();
    private MutableLiveData<String> registerMessage = new MutableLiveData<>();

    private FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();

    public LiveData<Boolean> getOnRegister() {
        return onRegister;
    }

    public LiveData<String> getRegisterMessage() {
        return registerMessage;
    }

    public void register(FragmentActivity activity, String username, String email, String password) {
        if (TextUtils.isEmpty(username)) {
            registerMessage.postValue("Username cannot be empty.");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            registerMessage.postValue("Email cannot be empty.");

            return;
        }

        if (TextUtils.isEmpty(password)) {
            registerMessage.postValue("Password cannot be empty.");
            return;
        }

        if (password.length() < 6) {
            registerMessage.postValue("Password length must not be less than 6 characters.");
            return;
        }

        authManager.register(activity, this, email, password);
    }

    @Override
    public void onSuccess() {
        Timber.d("Register success!");
        registerMessage.postValue("Register success!");
        onRegister.postValue(true);
    }

    @Override
    public void onFailure() {
        Timber.d("Register failed!");
        registerMessage.postValue("Register failed!");
        onRegister.postValue(false);
    }
}