package com.jgt.twitter.ui.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentRegisterBinding;
import com.jgt.twitter.ui.UIUtils;
import com.jgt.twitter.ui.feed.FeedActivity;
import com.jgt.twitter.utils.SharedPrefUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (AppCompatActivity) getActivity();

        //init views
        binding = FragmentRegisterBinding.bind(view);
        binding.btnRegister.setOnClickListener(this);

        //init view model
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.getOnRegister().observe(getViewLifecycleOwner(), this::onRegister);
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), this::onToastMessage);

        UIUtils.setUpToolbar(activity, true, getString(R.string.fragment_register_label));
    }

    private void onToastMessage(String message) {
        UIUtils.showToast(getActivity(), message);
    }

    private void onRegister(Boolean isRegistered) {
        if (null != isRegistered && isRegistered) {
            viewModel.addUserToDb(binding.etUsername.getText().toString(),
                    binding.etEmail.getText().toString());
            SharedPrefUtils.get().saveCredentials(binding.etEmail.getText().toString(),
                    binding.etPassword.getText().toString());
            startFeedActivity();
        }
    }

    private void startFeedActivity() {
        startActivity(new Intent(activity, FeedActivity.class));
        activity.finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnRegister:
                viewModel.register(getActivity(),
                        binding.etUsername.getText().toString(),
                        binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
                break;
            default:
                break;
        }
    }
}