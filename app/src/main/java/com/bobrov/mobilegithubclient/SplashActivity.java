package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    public static final String EXTRA_USER_DATA_KEY = "userKey";
    SharedPreferences sp;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        checkActiveSessions();
    }

    private void checkActiveSessions() {
        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
        String token = sp.getString("Token", null);
        if (token == null) {
            startActivity(new Intent(this, LoginBasicActivity.class));
        } else {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}
