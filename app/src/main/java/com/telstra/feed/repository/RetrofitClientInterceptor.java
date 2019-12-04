package com.telstra.feed.repository;

import android.content.Context;

import com.telstra.feed.util.ConnectivityException;
import com.telstra.feed.util.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetrofitClientInterceptor implements Interceptor {

    private Context mContext;

    RetrofitClientInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtil.isInternetAvailable(mContext)) {
            throw new ConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
