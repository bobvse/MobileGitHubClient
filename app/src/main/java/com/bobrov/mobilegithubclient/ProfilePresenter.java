package com.bobrov.mobilegithubclient;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bobrov.mobilegithubclient.Responses.UserResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> {

    private SharedPreferences sp;
    private GitHubApi api;

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

}
