package com.example.hotelsapp.server;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String BASE_URL = "https://tripadvisor16.p.rapidapi.com/api/v1/hotels/";
    private final static String apiKey = "972647ba51msh78af9e52448283bp1b047bjsn088284902969";
    private final static String apiHost = "tripadvisor16.p.rapidapi.com";

    private static RetrofitService retrofitService = new RetrofitService();
    private Retrofit retrofit;

    private RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("X-RapidAPI-Key", apiKey)
                        .addHeader("X-RapidAPI-Host", apiHost)
                        .build();
                return chain.proceed(request);
            }
        });

        // Build the Retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

    }

    public  Retrofit getRetrofit() {
        return retrofit;
    }

    public  static RetrofitService getInstance(){
        return retrofitService;
    }
}