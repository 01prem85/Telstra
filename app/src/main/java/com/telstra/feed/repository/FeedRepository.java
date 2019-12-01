package com.telstra.feed.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.telstra.feed.model.FeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedRepository {

    private static FeedRepository feedRepository;
    private FeedApi feedApi;
    private MutableLiveData<FeedResponse> feedData;

    private FeedRepository(Context context){
        feedApi = RetrofitService.createService(FeedApi.class, context);
    }

    public static FeedRepository getInstance(Context context){
        if (feedRepository == null){
            feedRepository = new FeedRepository(context);
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
                // set and send error message to ui
                FeedResponse feedResponse = new FeedResponse();
                feedResponse.setErrorMsg(t.getMessage());
                feedData.setValue(feedResponse);
            }
        });
        return feedData;
    }
}
