package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        checkActiveSessions();
    }

    private void checkActiveSessions(){
         sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
         String s = sp.getString("Token",null);
        if(s==null){
            startActivity(new Intent(this,MainActivity.class));
        }else{
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }
}
