package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bobrov.mobilegithubclient.Responses.CommitsResponse;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommitsActivity extends AppCompatActivity {
    private ReposResponse currentRepo;
    private List<CommitsResponse> commitsResponses;
    private GitHubApi api;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commits_activity);
        getData();
        loadRepos();
    }

    private void getData() {
        currentRepo = (ReposResponse) getIntent().getSerializableExtra(ReposActivity.EXTRA_REPOSITORY_KEY);
    }

    private void loadRepos() {
        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
        //String token = sp.getString("Token", null);
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getCommits(currentRepo.getOwner().getLogin(),currentRepo.getName()).enqueue(new Callback<List<CommitsResponse>>() {
            @Override
            public void onResponse(Call<List<CommitsResponse>> call, Response<List<CommitsResponse>> response) {
                commitsResponses = response.body();
                commitsResponses.clear();
            }

            @Override
            public void onFailure(Call<List<CommitsResponse>> call, Throwable t) {

            }
        });
    }
}
