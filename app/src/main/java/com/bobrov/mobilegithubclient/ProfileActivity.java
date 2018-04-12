package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sp;
    private TextView idTV;
    private TextView nameTV;
    private TextView reposTV;
    private GitHubApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        initComponents();
        loadUserData();
    }

    private void loadUserData() {
        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
        //String token = sp.getString("Token", null);
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getUser().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    setDataIntoView(response.body());
                } else {
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                //TODO обработка ошибок (спросить как)
                t.getMessage();
            }
        });
    }

    private void initComponents() {
        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
        findViewById(R.id.profile_activity_logout_button).setOnClickListener(this);
        findViewById(R.id.profile_activity_repos_button).setOnClickListener(this);

        idTV = findViewById(R.id.profile_activity_id_texView);
        nameTV = findViewById(R.id.profile_activity_name_texView);
        reposTV = findViewById(R.id.profile_activity_repos_texView);
    }

    private void setDataIntoView(UserResponse userResponse) {
        //TODO nullpointerexeption
        idTV.setText(userResponse.getId());
        nameTV.setText(userResponse.getUsername());
        reposTV.setText(userResponse.getPublicRepos());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_activity_logout_button:
                deleteSession();
               // clearToken();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.profile_activity_repos_button:
                startActivity(new Intent(this,ReposActivity.class));
                break;
        }
    }

    private void clearToken() {
        SharedPreferences.Editor e = sp.edit();
        e.remove("Token");
        e.remove("id");
        e.apply();
    }

    private void deleteSession() {
        String id = sp.getString("id", null);
        String token = sp.getString("BasicToken", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.deleteSession(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Integer i = response.code();
                if(i==204){
                    clearToken();
                    Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String i2 = t.getMessage();
                Toast.makeText(getApplicationContext(),i2,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
