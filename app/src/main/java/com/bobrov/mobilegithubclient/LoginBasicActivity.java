package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginBasicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MY_SETTINGS = "my_settings";

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
        gsonMake();

    }

    private void initComponents() {
        findViewById(R.id.login_activity_ok_button).setOnClickListener(this);
        findViewById(R.id.login_activity_cancel_button).setOnClickListener(this);
        loginInput = findViewById(R.id.login_activity_editext_login);
        passwordInput = findViewById(R.id.login_activity_input_password_editext);

        sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);

    }

    private void setConfigData() {
        authModel = new AuthModel();
        authModel.setScopes(Arrays.asList("user", "repo", "gist", "notifications", "read:org"));
        authModel.setNote(BuildConfig.NOTE_STRING);
        authModel.setClientId(BuildConfig.CLIENT_ID);
        authModel.setClientSecret(BuildConfig.CLIENT_SECRET);
        authModel.setNoteUrl("");
    }

    private Gson gsonMake() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setPrettyPrinting()
                .create();
        return gson;
    }

    private static OkHttpClient provideOkHttpClient(@Nullable String authToken, @Nullable String otp) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new AuthenticationInterceptor(authToken));
        client.addInterceptor(logger);
        return client.build();
    }

    private static Retrofit provideRetrofit(String authToken, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(provideOkHttpClient(authToken, null))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_activity_ok_button:
                authToken = EncodeHelper.basic(loginInput.getText().toString(), passwordInput.getText().toString());
                api = provideRetrofit(authToken, gsonMake()).create(GitHubApi.class);
                Toast.makeText(this, authToken, Toast.LENGTH_SHORT).show();

                api.doLogin(authModel).enqueue(new Callback<LoginData>() {
                    @Override
                    public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                        LoginData loginData = response.body();
                        SharedPreferences.Editor e = sp.edit();
                        e.putString("Token", loginData.getToken());
                        e.apply();
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
