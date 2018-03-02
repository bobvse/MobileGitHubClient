package com.bobrov.mobilegithubclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView twLogin;
    private TextView twPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        twLogin = findViewById(R.id.main_activity_login_editText);
        twPassword = findViewById(R.id.main_activity_password_editText);

        findViewById(R.id.main_activity_login_button).setOnClickListener(this);
        findViewById(R.id.main_activity_logout_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_login_button:
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_activity_logout_button:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
