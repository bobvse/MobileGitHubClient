package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginBasicActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MY_SETTINGS = "my_settings";
    private static final String CLIENT_ID = "f8c5756527291555ea68";
    private static final String CLIENT_SECRET = "1f985d37e1cc86a17b44b2882ab330fb0508ccd3";

    private EditText loginInput;
    private EditText passwordInput;
    SharedPreferences sp;
    private String authToken;
    public AuthModel authModel;
    private GitHubApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_basic_activity);
        initComponents();
        setConfigData();
    }

    private void initComponents() {
        findViewById(R.id.login_activity_ok_button).setOnClickListener(this);
        findViewById(R.id.login_activity_cancel_button).setOnClickListener(this);
        loginInput = findViewById(R.id.login_activity_editext_login);
        passwordInput = findViewById(R.id.login_activity_input_password_editext);

        sp = getSharedPreferences(MY_SETTINGS,Context.MODE_PRIVATE);
    }

    private void setConfigData() {
        //TODO getConfigData
        authModel = new AuthModel();
        authModel.setScopes(Arrays.asList("user", "repo", "gist", "notifications", "read:org"));
        authModel.setNote("Test");
        authModel.setClientId(CLIENT_ID);
        authModel.setClientSecret(CLIENT_SECRET);
        authModel.setNoteUrl("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_activity_ok_button:
                authToken = EncodeHelper.basic(loginInput.getText().toString(), passwordInput.getText().toString());
                api = RetrofitSingleton.getInstance().init(authToken).create(GitHubApi.class);
                Toast.makeText(this, authToken, Toast.LENGTH_SHORT).show();

                //TODO progress visible

                api.doLogin(authModel).enqueue(new Callback<LoginData>() {
                    @Override
                    public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                        //TODO progress gone
                        LoginData loginData = response.body();
                        //TODO constant
                        SharedPreferences.Editor e = sp.edit();
                        e.putString("Token", loginData.getToken());
                        e.putString("id",loginData.getId());
                        e.putString("BasicToken",authToken);
                        e.apply();
                        startActivity(new Intent(LoginBasicActivity.this, ProfileActivity.class));
                    }

                    @Override
                    public void onFailure(Call<LoginData> call, Throwable t) {

                    }
                });
                break;
            case R.id.login_activity_cancel_button:
                Toast.makeText(this, sp.getString("Token", "null"), Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
