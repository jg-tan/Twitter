package com.jgt.twitter.ui.feed.feed;

import com.jgt.twitter.firebase.auth.FirebaseAuthManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<Boolean> onSignOut = new MutableLiveData<>();
    private MutableLiveData<String> signoutMessage = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();

    private FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();

    public LiveData<Boolean> getOnSignOut() {
        return onSignOut;
    }

    public LiveData<String> getSignOutMessage() {
        return signoutMessage;
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public void loadCurrentUser() {
        authManager.loadCurrentUser();
    }

    public void signOut() {
        authManager.signOut();
        boolean isLoggedOff = !authManager.isLoggedIn();
        onSignOut.postValue(isLoggedOff);
        signoutMessage.postValue(isLoggedOff ? "Logged off!" : "Logged off failed!");
    }
}