package com.telstra.feed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.telstra.feed.R;
import com.telstra.feed.databinding.FeedItemBinding;
import com.telstra.feed.model.FeedRow;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private Context mContext;
    private ArrayList<FeedRow> feedRows;

    public FeedAdapter(Context context, ArrayList<FeedRow> feedRows) {
        this.mContext = context;
        this.feedRows = feedRows;
    }

    @NonNull
    @Override
    public FeedAdapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FeedItemBinding feedItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.feed_item, parent, false);
        return new FeedViewHolder(feedItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.FeedViewHolder holder, int position) {
        FeedRow feedRow = feedRows.get(position);
        holder.feedItemBinding.setFeedItem(feedRow);
    }

    @Override
    public int getItemCount() {
        return feedRows.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {

        FeedItemBinding feedItemBinding;

        FeedViewHolder(@NonNull FeedItemBinding feedItemBinding) {
            super(feedItemBinding.getRoot());
            this.feedItemBinding = feedItemBinding;
        }
    }
}
