package com.telstra.feed.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.telstra.feed.model.FeedResponse;
import com.telstra.feed.repository.FeedRepository;
import com.telstra.feed.util.NetworkUtil;

public class FeedViewModel extends AndroidViewModel {

    private MutableLiveData<FeedResponse> mutableLiveData;
    private FeedRepository feedRepository;

    public FeedViewModel(@NonNull Application application) {
        super(application);
    }

    private LiveData<FeedResponse> init(){
        // check for network connectivity
        if(NetworkUtil.isInternetAvailable(getApplication())) {
            feedRepository = FeedRepository.getInstance(getApplication());
            mutableLiveData = feedRepository.getFeedData();
        }
        return mutableLiveData;
    }

    public LiveData<FeedResponse> getFeedRepository() {
        if (mutableLiveData != null){
            return mutableLiveData;
        } else {
            return init();
        }
    }
}
