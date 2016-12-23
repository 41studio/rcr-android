package com.fourtyonestudio.rcr.networks;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class RestApi {
    private static final String BASE_URL = "https://room-cleanliness-ratings.herokuapp.com/api/v1/";
    private final ApiInterface api;

    public RestApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        api = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getApi() {
        return api;
    }
}
