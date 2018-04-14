package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.bobrov.mobilegithubclient.Responses.BranchResponse;
import com.bobrov.mobilegithubclient.Responses.CommitsResponse;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommitsActivity extends AppCompatActivity{
    private ReposResponse currentRepo;
    private List<CommitsResponse> commitsResponses;
    private List<BranchResponse> branches;
    private GitHubApi api;
    SharedPreferences sp;
    private CommitsListAdapter commitsListAdapter;
    private Spinner checkBranch;
    private BranchesListAdapter branchesListAdapter;
    ListView commitsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commits_activity);

        checkBranch = findViewById(R.id.branch_spinner);

        getData();
        commitsListView = findViewById(R.id.commits_list_view);
        branchesListAdapter = new BranchesListAdapter(this);
        commitsListAdapter = new CommitsListAdapter(this);
        checkBranch.setAdapter(branchesListAdapter);

        commitsListView.setAdapter(commitsListAdapter);

        loadCommits();
        loadBranches();
      //  checkBranchesCommits();
    }

    private void getData() {
        currentRepo = (ReposResponse) getIntent().getSerializableExtra(ReposActivity.EXTRA_REPOSITORY_KEY);
    }

    private void loadCommits() {
        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
        //String token = sp.getString("Token", null);
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getCommits(currentRepo.getOwner().getLogin(), currentRepo.getName()).enqueue(new Callback<List<CommitsResponse>>() {
            @Override
            public void onResponse(Call<List<CommitsResponse>> call, Response<List<CommitsResponse>> response) {
                commitsResponses = response.body();
                commitsListAdapter.setData(commitsResponses);
            }

            @Override
            public void onFailure(Call<List<CommitsResponse>> call, Throwable t) {

            }
        });
    }

    private void loadBranches() {
        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
        //String token = sp.getString("Token", null);
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getBranches(currentRepo.getOwner().getLogin(), currentRepo.getName()).enqueue(new Callback<List<BranchResponse>>() {
            @Override
            public void onResponse(Call<List<BranchResponse>> call, Response<List<BranchResponse>> response) {
                branches = response.body();
                branchesListAdapter.setData(branches);
            }

            @Override
            public void onFailure(Call<List<BranchResponse>> call, Throwable t) {

            }
        });
    }

//    private void checkBranchesCommits() {
//        checkBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                BranchResponse br1 = (BranchResponse) branchesListAdapter.getItem(position);
//                loadCommitsBranches(br1.getCommit().getSha());
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                commitsListAdapter.notifyDataSetChanged();
//            }
//        });
//    }

//    private void loadCommitsBranches(String sha) {
//        sp = getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE);
//        //String token = sp.getString("Token", null);
//        String token = sp.getString("Token", null);
//        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
//
//        api.getBranchCommits(currentRepo.getOwner().getLogin(),currentRepo.getName(),sha).enqueue(new Callback<List<CommitsResponse>>() {
//            @Override
//            public void onResponse(Call<List<CommitsResponse>> call, Response<List<CommitsResponse>> response) {
//                commitsResponses = response.body();
//                commitsListAdapter.setData(commitsResponses);
//                commitsListAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<CommitsResponse>> call, Throwable t) {
//
//            }
//        });
//    }

}
