package com.bobrov.mobilegithubclient;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Responses.UserResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> {

    private SharedPreferences sp;
    private GitHubApi api;
    private List<ReposResponse> repos = new ArrayList<>();

    public ProfilePresenter() {
    }

    public ProfilePresenter(SharedPreferences sp) {
        this.sp = sp;
    }

    public void loadUserData() {
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getUser().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    getViewState().setDataIntoView(response.body());
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

    public void loadRepos() {
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getRepos().enqueue(new Callback<List<ReposResponse>>() {
            @Override
            public void onResponse(Call<List<ReposResponse>> call, Response<List<ReposResponse>> response) {
                repos = response.body();
                getViewState().showReposList(repos);
            }

            @Override
            public void onFailure(Call<List<ReposResponse>> call, Throwable t) {

            }

        });
    }

    public void deleteSession() {
        String id = sp.getString("id", null);
        String token = sp.getString("BasicToken", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.deleteSession(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Integer i = response.code();
                if (i == 204) {
                    clearToken();
                    getViewState().startLoginActivity();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String i2 = t.getMessage();
            }
        });
    }

    private void clearToken() {
        SharedPreferences.Editor e = sp.edit();
        e.remove("Token");
        e.remove("id");
        e.apply();
    }

}
