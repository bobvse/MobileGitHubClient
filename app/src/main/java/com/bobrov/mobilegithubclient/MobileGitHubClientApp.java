package com.bobrov.mobilegithubclient;

import android.app.Application;

/**
 * Created by Vladimir on 16.03.2018.
 */

public class MobileGitHubClientApp extends Application {
    @Override
    public void onCreate() {
        RetrofitSingleton.getInstance().init(getApplicationContext());
        super.onCreate();
    }
}
