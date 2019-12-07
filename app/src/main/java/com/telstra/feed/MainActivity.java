package com.telstra.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.telstra.feed.adapter.FeedAdapter;
import com.telstra.feed.model.FeedResponse;
import com.telstra.feed.model.FeedRow;
import com.telstra.feed.viewmodel.FeedViewModel;

import java.util.ArrayList;

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
        if(mFeedViewModel.getFeedRepository() != null) {
            mFeedViewModel.getFeedRepository().observe(this, this::updateAdapterData);
        } else {
            Toast.makeText(this, getString(R.string.network_error_msg), Toast.LENGTH_SHORT).show();
        }

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            if(mFeedViewModel.getFeedRepository() != null) {
                mFeedViewModel.getFeedRepository().observe(MainActivity.this, feedResponse -> {
                    updateAdapterData(feedResponse);
                    mSwipeRefreshLayout.setRefreshing(false);
                });
            } else {
                Toast.makeText(this, getString(R.string.network_error_msg), Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        setupRecyclerView();
    }

    private void updateAdapterData(FeedResponse feedResponse){
        if(feedResponse.getErrorMsg() != null){
            Toast.makeText(this, feedResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
        } else {
            feedRowArrayList.clear();
            setTitle(feedResponse.getTitle());
            feedRowArrayList.addAll(feedResponse.getRows());
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        mFeedAdapter = new FeedAdapter(MainActivity.this, feedRowArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFeedAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
