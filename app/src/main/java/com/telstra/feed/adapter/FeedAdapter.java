package com.telstra.feed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.telstra.feed.R;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.feed_item, parent, false);
        return new  FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.FeedViewHolder holder, int position) {
        if(feedRows.get(position).getTitle() == null && feedRows.get(position).getDescription() == null &&
                feedRows.get(position).getImageHref() == null) {
            holder.layoutView.setVisibility(View.GONE);
        } else {
            holder.tvName.setText(feedRows.get(position).getTitle());
            holder.tvDescription.setText(feedRows.get(position).getDescription());
            if(feedRows.get(position).getImageHref() != null){
                Picasso.get().load(feedRows.get(position).getImageHref()).error(R.drawable.ic_error_loading).into(holder.ivFeed);
            } else {
                holder.ivFeed.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return feedRows.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDescription;
        ImageView ivFeed;
        View layoutView;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutView = itemView.findViewById(R.id.feed_item_layout);
            tvName = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivFeed = itemView.findViewById(R.id.iv_feed);

        }
    }
}
