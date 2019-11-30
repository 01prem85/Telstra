package com.telstra.feed.repository;

import androidx.lifecycle.MutableLiveData;

import com.telstra.feed.model.FeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedRepository {

    private static FeedRepository feedRepository;
    private FeedApi feedApi;
    private MutableLiveData<FeedResponse> feedData;

    private FeedRepository(){
        feedApi = RetrofitService.createService(FeedApi.class);
    }

    public static FeedRepository getInstance(){
        if (feedRepository == null){
            feedRepository = new FeedRepository();
        }
        return feedRepository;
    }

    public MutableLiveData<FeedResponse> getFeedData(){
        feedData = new MutableLiveData<>();
        feedApi.getFeedList().enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.isSuccessful()){
                    feedData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                feedData.setValue(null);
            }
        });
        return feedData;
    }
}
