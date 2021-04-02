package com.jgt.twitter.ui.feed.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentFeedBinding;
import com.jgt.twitter.ui.UIUtils;
import com.jgt.twitter.ui.auth.AuthActivity;
import com.jgt.twitter.utils.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FeedFragment extends Fragment implements View.OnClickListener {

    private NavController navController;
    private FragmentFeedBinding binding;
    private FeedViewModel viewModel;
    private FeedAdapter adapter;
    private ActionBar actionBar;
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
        navController = Navigation.findNavController(view);
        activity = (AppCompatActivity) getActivity();
        adapter = new FeedAdapter();

        adapter.setOnClickListener(this);
        adapter.setTweetList(Util.getSampleTweetList(10));
        binding = FragmentFeedBinding.bind(view);
        binding.rvFeed.setHasFixedSize(true);
        binding.rvFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvFeed.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        viewModel.getOnSignOut().observe(getViewLifecycleOwner(), this::onSignOut);
        viewModel.getSignOutMessage().observe(getViewLifecycleOwner(), this::onSignOutMessage);

        UIUtils.setUpToolbar(activity, false, getString(R.string.fragment_feed_label));

        viewModel.loadCurrentUser();
    }

    private void onSignOut(Boolean isSignedOut) {
        if (null != isSignedOut && isSignedOut) {
            startActivity(new Intent(activity, AuthActivity.class));
        }
    }

    private void onSignOutMessage(String message) {
        UIUtils.showToast(activity, message);
    }

    @Override
    public void onClick(View view) {
        navController.navigate(R.id.on_tweet_clicked);
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