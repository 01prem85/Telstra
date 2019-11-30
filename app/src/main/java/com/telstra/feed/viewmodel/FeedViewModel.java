package com.telstra.feed.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.telstra.feed.model.FeedResponse;
import com.telstra.feed.repository.FeedRepository;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<FeedResponse> mutableLiveData;
    private FeedRepository feedRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        feedRepository = FeedRepository.getInstance();
        mutableLiveData = feedRepository.getFeedData();
    }

    public LiveData<FeedResponse> getFeedRepository() {
        return mutableLiveData;
    }
}
