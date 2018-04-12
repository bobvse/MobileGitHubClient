package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposActivity extends AppCompatActivity {
    private GitHubApi api;
    SharedPreferences sp;
    private List<ReposResponse> repos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repos_activity);
        loadRepos();
    }

    private void loadRepos() {
        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
        //String token = sp.getString("Token", null);
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getRepos().enqueue(new Callback<List<ReposResponse>>() {
            @Override
            public void onResponse(Call<List<ReposResponse>> call, Response<List<ReposResponse>> response) {
                repos = response.body();
                  Toast.makeText(getApplicationContext(), repos.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ReposResponse>> call, Throwable t) {

            }

       });
    }
}
