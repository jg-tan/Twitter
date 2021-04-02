package com.jgt.twitter.ui.auth.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentRegisterBinding;
import com.jgt.twitter.firebase.auth.FirebaseAuthListener;
import com.jgt.twitter.firebase.auth.FirebaseAuthManager;
import com.jgt.twitter.ui.UIUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import timber.log.Timber;

public class RegisterFragment extends Fragment implements View.OnClickListener, FirebaseAuthListener {
    private static final String TAG = RegisterFragment.class.getSimpleName();
    private NavController navController;
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;
    private ActionBar actionBar;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding = FragmentRegisterBinding.bind(view);
        activity = (AppCompatActivity) getActivity();

        binding.btnRegister.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.getOnRegister().observe(getViewLifecycleOwner(), this::onRegister);

        UIUtils.setUpToolbar(activity, true, getString(R.string.fragment_register_label));
    }

    private void onRegister(Boolean aBoolean) {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnRegister:
                FirebaseAuthManager.getInstance().register(getActivity(),
                        this,
                        binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess() {
        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();
        Timber.d(user.getEmail());
        Timber.d(user.getUid());
        UIUtils.showToast(activity, "Register success!");
    }

    @Override
    public void onFailure() {
        UIUtils.showToast(activity, "Register failed!");
    }
}