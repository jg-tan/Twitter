package com.jgt.twitter.ui.feed.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgt.twitter.R;
import com.jgt.twitter.databinding.ItemFeedBinding;
import com.jgt.twitter.firebase.db.entity.Tweet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedItemViewHolder> {

    private List<Tweet> tweetList;
    private View.OnClickListener onClickListener;

    @NonNull
    @Override
    public FeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedItemViewHolder holder, int position) {
        holder.setTweet(tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        if (null != tweetList) {
            return tweetList.size();
        }
        return 0;
    }

    public void setTweetList(List<Tweet> tweetList) {
        this.tweetList = tweetList;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class FeedItemViewHolder extends RecyclerView.ViewHolder {
        private ItemFeedBinding binding;

        public FeedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemFeedBinding.bind(itemView);
        }

        void setTweet(Tweet tweet) {
            binding.tvUsername.setText(tweet.getUsername());
            binding.tvBody.setText(tweet.getBody());
            binding.tvDate.setText(Long.toString(tweet.getTimestamp()));
            binding.layoutTweet.setOnClickListener(onClickListener);
        }
    }
}