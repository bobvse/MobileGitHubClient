package com.bobrov.mobilegithubclient.Retrofit;

import android.support.annotation.Nullable;

import com.bobrov.mobilegithubclient.AuthenticationInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    public Retrofit init(String authToken) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gsonMake()))
                .client(provideOkHttpClient(authToken))
                .build();
        return retrofit;
    }

    public Retrofit get() {
        return retrofit;
    }

    private Gson gsonMake() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setPrettyPrinting()
                .create();
        return gson;
    }

    private static OkHttpClient provideOkHttpClient(@Nullable String authToken) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new AuthenticationInterceptor(authToken));
        client.addInterceptor(logger);
        return client.build();
    }

}

