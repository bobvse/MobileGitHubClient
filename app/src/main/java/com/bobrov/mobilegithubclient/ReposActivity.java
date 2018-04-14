package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static String EXTRA_REPOSITORY_KEY = "repo_key";

    private GitHubApi api;
    SharedPreferences sp;
    private List<ReposResponse> repos = new ArrayList<>();
    private ReposListAdapter reposListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repos_activity);
        ListView reposListView = findViewById(R.id.exercises_list_view);
        reposListAdapter = new ReposListAdapter(this);
        reposListView.setAdapter(reposListAdapter);
        reposListView.setOnItemClickListener(this);
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
                reposListAdapter.setData(repos);
            }

            @Override
            public void onFailure(Call<List<ReposResponse>> call, Throwable t) {

            }

       });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(this, CommitsActivity.class);
        intent.putExtra(EXTRA_REPOSITORY_KEY, (ReposResponse) reposListAdapter.getItem(position));
        startActivity(intent);
    }
}
