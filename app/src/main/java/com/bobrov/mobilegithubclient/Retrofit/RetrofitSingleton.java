package com.bobrov.mobilegithubclient.Retrofit;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vladimir on 02.03.2018.
 */

public class RetrofitSingleton {
    private static volatile RetrofitSingleton instance;
    private Retrofit retrofit;

    public static RetrofitSingleton getInstance() {
        if (instance == null) {
            synchronized (RetrofitSingleton.class) {
                if (instance == null) {
                    instance = new RetrofitSingleton();
                }
            }
        }
        return instance;
    }
    private RetrofitSingleton() { }

    public void init() {

    }

    public Retrofit getRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}

