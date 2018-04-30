package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

public class LoginBasicActivity extends MvpAppCompatActivity implements LoginBasicView, View.OnClickListener {
    public static final String MY_SETTINGS = "my_settings";

    private EditText loginInput;
    private EditText passwordInput;
    private RelativeLayout progress_login;


    @InjectPresenter
    LoginBasicPresenter loginBasicPresenter;

    @ProvidePresenter
    public LoginBasicPresenter providePresenter() {
        return new LoginBasicPresenter(getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_basic_activity);
        initComponents();
    }

    private void initComponents() {
        findViewById(R.id.login_activity_ok_button).setOnClickListener(this);
        findViewById(R.id.login_activity_cancel_button).setOnClickListener(this);
        progress_login = findViewById(R.id.relative_progress_login_activity);
        loginInput = findViewById(R.id.login_activity_editext_login);
        passwordInput = findViewById(R.id.login_activity_input_password_editext);

        progress_login.setVisibility(RelativeLayout.GONE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_activity_ok_button:
                loginBasicPresenter.doLogin(loginInput.getText().toString(), passwordInput.getText().toString());
                break;
            case R.id.login_activity_cancel_button:
                finish();
                break;
        }
    }

    @Override
    public void showProgress() {
        progress_login.setVisibility(RelativeLayout.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_login.setVisibility(RelativeLayout.GONE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void startProfile() {
        startActivity(new Intent(this, ProfileActivity.class));
    }
}
