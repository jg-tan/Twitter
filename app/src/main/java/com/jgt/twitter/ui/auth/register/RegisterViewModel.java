package com.jgt.twitter.ui.auth.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = RegisterViewModel.class.getSimpleName();
    private MutableLiveData<Boolean> onRegister =  new MutableLiveData<>();

    public LiveData<Boolean> getOnRegister() {
        return onRegister;
    }
}