package com.bobrov.mobilegithubclient;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bobrov.mobilegithubclient.Responses.BranchResponse;
import com.bobrov.mobilegithubclient.Responses.CommitsResponse;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class CommitsPresenter extends MvpPresenter<CommitsView> {

    private SharedPreferences sp;
    private GitHubApi api;

    private List<CommitsResponse> commitsResponses;
    private List<BranchResponse> branches;
    private ReposResponse currentRepo;

    public CommitsPresenter() {

    }

    public CommitsPresenter(SharedPreferences sp) {
        this.sp = sp;
    }

    public void loadBranches(ReposResponse repo) {
        getViewState().showProgress();
        String token = sp.getString("Token", null);
        currentRepo = repo;
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getBranches(repo.getOwner().getLogin(), repo.getName()).enqueue(new Callback<List<BranchResponse>>() {
            @Override
            public void onResponse(Call<List<BranchResponse>> call, Response<List<BranchResponse>> response) {
                branches = response.body();
                getViewState().showBranches(branches);
                getViewState().hideProgress();
            }

            @Override
            public void onFailure(Call<List<BranchResponse>> call, Throwable t) {
            }
        });
    }

    public void loadCommitsBranches(String sha) {
        getViewState().showProgress();
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);

        api.getBranchCommits(currentRepo.getOwner().getLogin(), currentRepo.getName(), sha).enqueue(new Callback<List<CommitsResponse>>() {
            @Override
            public void onResponse(Call<List<CommitsResponse>> call, Response<List<CommitsResponse>> response) {
                commitsResponses = response.body();
                getViewState().showCommitsList(commitsResponses);
                getViewState().showCountCommits(commitsResponses.size());
                getViewState().hideProgress();
            }

            @Override
            public void onFailure(Call<List<CommitsResponse>> call, Throwable t) {

            }
        });


    }
}
