package com.telstra.feed.repository;

import com.telstra.feed.model.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedApi {

    @GET("facts.json")
    Call<FeedResponse> getFeedList();
}
