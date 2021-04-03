package com.jgt.twitter.ui.feed.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentFeedBinding;
import com.jgt.twitter.firebase.db.entity.Tweet;
import com.jgt.twitter.ui.UIUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FeedFragment extends Fragment implements View.OnClickListener {

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

        adapter = new FeedAdapter();
        adapter.setOnClickListener(this);

        binding = FragmentFeedBinding.bind(view);
        binding.rvFeed.setHasFixedSize(true);
        binding.rvFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvFeed.setAdapter(adapter);
        binding.btnTweet.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        viewModel.getOnSignOut().observe(getViewLifecycleOwner(), this::onSignOut);
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), this::onToastMessage);
        viewModel.getUsername().observe(getViewLifecycleOwner(), this::onUsernameObtained);
        viewModel.getObsTweetList().observe(getViewLifecycleOwner(), this::onTweetsRetrieved);

        UIUtils.setUpToolbar(activity, false, getString(R.string.fragment_feed_label));

        viewModel.loadCurrentUser();
    }

    private void onTweetsRetrieved(List<Tweet> tweets) {
        adapter.setTweetList(tweets);
    }

    private void onUsernameObtained(String username) {
        binding.tvTweetUser.setText(username);
    }

    private void onSignOut(Boolean isSignedOut) {
        if (null != isSignedOut && isSignedOut) {
            activity.finish();
        }
    }

    private void onToastMessage(String message) {
        UIUtils.showToast(activity, message);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnTweet:
                viewModel.addTweet(binding.etTweetBody.getText().toString(),
                        System.currentTimeMillis());
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
}