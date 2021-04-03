package com.jgt.twitter.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentLoginBinding;
import com.jgt.twitter.ui.UIUtils;
import com.jgt.twitter.ui.feed.FeedActivity;
import com.jgt.twitter.utils.SharedPrefUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import timber.log.Timber;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;
    private NavController navController;
    private LoginViewModel viewModel;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding = FragmentLoginBinding.bind(view);
        activity = (AppCompatActivity) getActivity();

        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getOnLogin().observe(getViewLifecycleOwner(), this::onLogin);
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), this::onToastMessage);

        UIUtils.setUpToolbar(activity, false, getString(R.string.fragment_login_label));

        if (SharedPrefUtils.get().isLoggedIn()) {
            login(SharedPrefUtils.get().getEmail(), SharedPrefUtils.get().getPassword());
        }
    }

    private void onToastMessage(String message) {
        UIUtils.showToast(activity, message);
    }

    private void onLogin(Boolean isLoggedIn) {
        if (null != isLoggedIn && isLoggedIn) {
            SharedPrefUtils.get().saveCredentials(binding.etEmail.getText().toString(),
                    binding.etPassword.getText().toString());
            startFeedActivity();
        }
    }

    private void login(String email, String password) {
        viewModel.login(activity,
                email,
                password);
    }

    private void startFeedActivity() {
        startActivity(new Intent(activity, FeedActivity.class));
        activity.finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnLogin:
                login(binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
                break;
            case R.id.btnRegister:
                navController.navigate(R.id.on_go_to_register);
                break;
            default:
                break;
        }
    }
}