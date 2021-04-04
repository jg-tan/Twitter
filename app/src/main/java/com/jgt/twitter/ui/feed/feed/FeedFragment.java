package com.jgt.twitter.ui.feed.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentFeedBinding;
import com.jgt.twitter.firebase.db.entity.Tweet;
import com.jgt.twitter.ui.UIUtils;
import com.jgt.twitter.ui.auth.AuthActivity;
import com.jgt.twitter.utils.SharedPrefUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FeedFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private FragmentFeedBinding binding;
    private FeedViewModel viewModel;
    private FeedAdapter adapter;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        activity.getMenuInflater().inflate(R.menu.feed_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (AppCompatActivity) getActivity();

        //init adapter
        adapter = new FeedAdapter();
        adapter.setOnClickListener(this);

        //init views
        binding = FragmentFeedBinding.bind(view);
        binding.rvFeed.setHasFixedSize(true);
        binding.rvFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvFeed.setAdapter(adapter);
        binding.btnTweet.setOnClickListener(this);
        binding.etTweetBody.addTextChangedListener(this);

        //init view model
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        viewModel.getOnSignOut().observe(getViewLifecycleOwner(), this::onSignOut);
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), this::onToastMessage);
        viewModel.getUsername().observe(getViewLifecycleOwner(), this::onUsernameObtained);
        viewModel.getObsTweetList().observe(getViewLifecycleOwner(), this::onTweetsRetrieved);
        viewModel.isLoadingError().observe(getViewLifecycleOwner(), this::onLoadingError);
        viewModel.getHasLoaded().observe(getViewLifecycleOwner(), this::onHasLoaded);

        UIUtils.setUpToolbar(activity, false, getString(R.string.fragment_feed_label));

        viewModel.loadCurrentUser();
    }

    private void onLoadingError(Boolean isLoadingError) {
        binding.pbFeedLoad.setVisibility(View.GONE);
        binding.tvError.setVisibility(View.VISIBLE);
    }

    private void onHasLoaded(Boolean hasLoaded) {
        if (hasLoaded) {
            binding.pbFeedLoad.setVisibility(View.GONE);
            binding.layoutFeed.setVisibility(View.VISIBLE);
        } else {
            binding.pbFeedLoad.setVisibility(View.VISIBLE);
            binding.layoutFeed.setVisibility(View.GONE);
            viewModel.loadCurrentUser();
        }
    }

    private void onTweetsRetrieved(List<Tweet> tweets) {
        adapter.setTweetList(tweets);
    }

    private void onUsernameObtained(String username) {
        binding.tvTweetUser.setText(username);
    }

    private void onSignOut(Boolean isSignedOut) {
        if (null != isSignedOut && isSignedOut) {
            SharedPrefUtils.get().clearCredentials();
            startAuthActivity();
        }
    }

    private void onToastMessage(String message) {
        UIUtils.showToast(activity, message);
    }

    private void startAuthActivity() {
        startActivity(new Intent(activity, AuthActivity.class));
        activity.finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnTweet:
                viewModel.addTweet(binding.etTweetBody.getText().toString(),
                        System.currentTimeMillis());
                hideSoftKeyboard();
                binding.etTweetBody.setText("");
                binding.etTweetBody.clearFocus();
                break;
            case R.id.btnDelete:
                Tweet tweet = (Tweet) view.getTag();
                viewModel.deleteTweet(tweet);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuLogout:
                viewModel.signOut();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
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
                binding.btnTweet.setEnabled(false);
            } else {
                binding.btnTweet.setEnabled(true);
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

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getView();
        if (null != view) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}