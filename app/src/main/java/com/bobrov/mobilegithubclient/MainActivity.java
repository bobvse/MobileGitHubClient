package com.bobrov.mobilegithubclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_login_standart_button:
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,LoginBasicActivity.class));
                break;
            case R.id.main_activity_loginOath_button:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_activity_loginToken_button:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,AccessTokenActivity.class));
                break;
        }
    }
}
