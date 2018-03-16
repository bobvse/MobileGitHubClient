package com.bobrov.mobilegithubclient;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Uri test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        findViewById(R.id.main_activity_login_standart_button).setOnClickListener(this);
        findViewById(R.id.main_activity_loginOath_button).setOnClickListener(this);
        findViewById(R.id.main_activity_loginToken_button).setOnClickListener(this);
    }

    private void OpeninBrovser(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_login_standart_button:
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
//                test = new Uri.Builder().scheme("https")
//                        .authority("github.com")
//                        .appendPath("login")
//                        .appendPath("oauth")
//                        .appendPath("authorize")
//                        .appendQueryParameter("client_id", "f8c5756527291555ea68")
//                        .appendQueryParameter("redirect_uri", "yandex.ru")
//                        .appendQueryParameter("scope", "user,repo,gist,notifications,read:org")
//                        .appendQueryParameter("state", BuildConfig.APPLICATION_ID)
//                        .build();
//                test.toString();
                break;
            case R.id.main_activity_loginOath_button:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_activity_loginToken_button:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
