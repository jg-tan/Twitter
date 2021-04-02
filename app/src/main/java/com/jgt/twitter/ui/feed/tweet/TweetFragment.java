package com.jgt.twitter.ui.feed.tweet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.FragmentTweetBinding;
import com.jgt.twitter.ui.UIUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

public class TweetFragment extends Fragment {
    private static final String TAG = TweetFragment.class.getSimpleName();
    private NavController navController;
    private FragmentTweetBinding binding;
    private TweetViewModel viewModel;
    private ActionBar actionBar;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_feed, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        activity.getMenuInflater().inflate(R.menu.tweet_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentTweetBinding.bind(view);
        activity = (AppCompatActivity) getActivity();

        UIUtils.setUpToolbar(activity, true, getString(R.string.fragment_tweet_label));
    }
}