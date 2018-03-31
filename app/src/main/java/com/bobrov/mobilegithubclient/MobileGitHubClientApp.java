package com.bobrov.mobilegithubclient;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import static com.bobrov.mobilegithubclient.LoginBasicActivity.MY_SETTINGS;

/**
 * Created by Vladimir on 16.03.2018.
 */

public class MobileGitHubClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
