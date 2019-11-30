package com.telstra.feed.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.telstra.feed.util.Constant.BASE_URL;

public class RetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}