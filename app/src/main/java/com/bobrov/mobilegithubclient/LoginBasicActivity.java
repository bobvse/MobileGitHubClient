package com.bobrov.mobilegithubclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginBasicActivity extends AppCompatActivity implements View.OnClickListener {
private EditText loginInput;
private EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_basic_activity);
        initComponents();
    }

    public void initComponents(){
        findViewById(R.id.login_activity_ok_button).setOnClickListener(this);
        findViewById(R.id.login_activity_cancel_button).setOnClickListener(this);
        loginInput = findViewById(R.id.login_activity_editext_login);
        passwordInput = findViewById(R.id.login_activity_input_password_editext);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_activity_ok_button:
                Toast.makeText(this, passwordInput.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_activity_cancel_button:
                finish();
                break;
        }
    }
}
