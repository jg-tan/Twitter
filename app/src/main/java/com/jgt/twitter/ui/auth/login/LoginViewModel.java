package com.jgt.twitter.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();
    private MutableLiveData<Boolean> onLogin = new MutableLiveData<>();

    public LiveData<Boolean> getOnLogin() {
        return onLogin;
    }
}