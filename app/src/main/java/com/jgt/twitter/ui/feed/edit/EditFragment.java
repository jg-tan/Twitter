package com.jgt.twitter.ui.feed.edit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentEditBinding;
import com.jgt.twitter.ui.UIUtils;
import com.jgt.twitter.utils.Constants;
import com.jgt.twitter.utils.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class EditFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private FragmentEditBinding binding;
    private NavController navController;
    private EditViewModel viewModel;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        activity = (AppCompatActivity) getActivity();

        //init views
        binding = FragmentEditBinding.bind(view);
        binding.btnEdit.setOnClickListener(this);
        binding.etTweetBody.addTextChangedListener(this);

        //init view model
        viewModel = new ViewModelProvider(this).get(EditViewModel.class);
        viewModel.getIsTweetLoaded().observe(getViewLifecycleOwner(), this::onTweetLoaded);

        UIUtils.setUpToolbar(activity, true, getString(R.string.fragment_edit_label));

        Bundle bundle = getArguments();
        if (null != bundle) {
            viewModel.loadTweet(Util.toTweet(bundle.getString(Constants.BUNDLE_TWEET)));
        }
    }

    private void onTweetLoaded(Boolean isTweetLoaded) {
        if (null != isTweetLoaded && isTweetLoaded) {
            binding.tvTweetUser.setText(viewModel.getUsername());
            binding.tvTweetDate.setText(viewModel.getTweetDate());
            binding.etTweetBody.setText(viewModel.getTweetBody());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int left = 280 - charSequence.length();
        if (left < 50) {
            if (left < 0) {
                left = 0;
                binding.btnEdit.setEnabled(false);
            } else {
                binding.btnEdit.setEnabled(true);
            }
            String count = left + " characters remaining.";
            if (left <= 1) {
                count = left + " character remaining.";
            }
            binding.tvCharCount.setText(count);
            binding.tvCharCount.setVisibility(View.VISIBLE);
        } else {
            binding.tvCharCount.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnEdit:
                viewModel.editTweet(binding.etTweetBody.getText().toString());
                navController.navigate(R.id.on_back_to_feed);
                break;
            default:
                break;
        }
    }
}