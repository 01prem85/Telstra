package com.telstra.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.telstra.feed.adapter.FeedAdapter;
import com.telstra.feed.model.FeedResponse;
import com.telstra.feed.model.FeedRow;
import com.telstra.feed.viewmodel.FeedViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FeedAdapter mFeedAdapter;
    FeedViewModel mFeedViewModel;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<FeedRow> feedRowArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_feed);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        mFeedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        mFeedViewModel.init();
        mFeedViewModel.getFeedRepository().observe(this, this::updateAdapterData);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            mFeedViewModel.getFeedRepository().observe(MainActivity.this, feedResponse -> {
                updateAdapterData(feedResponse);
                mSwipeRefreshLayout.setRefreshing(false);
            });
        });

        setupRecyclerView();
    }

    private void updateAdapterData(FeedResponse feedResponse){
        List<FeedRow> feedRowList = feedResponse.getRows();
        if(feedRowList != null){
            feedRowArrayList.clear();
            setTitle(feedResponse.getTitle());
            feedRowArrayList.addAll(feedRowList);
            mFeedAdapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerView() {
        if (mFeedAdapter == null) {
            mFeedAdapter = new FeedAdapter(MainActivity.this, feedRowArrayList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mFeedAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setNestedScrollingEnabled(true);
        } else {
            mFeedAdapter.notifyDataSetChanged();
        }
    }
}
