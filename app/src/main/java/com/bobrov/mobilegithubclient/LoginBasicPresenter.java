package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bobrov.mobilegithubclient.Responses.LoginData;
import com.bobrov.mobilegithubclient.Retrofit.AuthModel;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class LoginBasicPresenter extends MvpPresenter<LoginBasicView> {

    private static final String CLIENT_ID = "f8c5756527291555ea68";
    private static final String CLIENT_SECRET = "1f985d37e1cc86a17b44b2882ab330fb0508ccd3";

    private SharedPreferences sp;

    public LoginBasicPresenter(SharedPreferences sp) {
        this.sp = sp;
    }

    public void doLogin(String login, String passowrd) {
        getViewState().showProgress();

        final String authToken = EncodeHelper.basic(login, passowrd);
        GitHubApi api = RetrofitSingleton.getInstance().init(authToken).create(GitHubApi.class);

        api.doLogin(getConfigData()).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if(response.code()!=422) {
                    LoginData loginData = response.body();

                    //TODO constant
                    SharedPreferences.Editor e = sp.edit();
                    e.putString("Token", loginData.getToken());
                    e.putString("id", loginData.getId());
                    e.putString("BasicToken", authToken);
                    e.apply();

                    getViewState().hideProgress();
                    getViewState().startProfile();
                }else {
                    //TODO SHOW ERROR
                }

            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                //TODO что передать?
                getViewState().hideProgress();
                getViewState().showError(t.getMessage());
            }
        });
    }

    private AuthModel getConfigData() {
        AuthModel authModel = new AuthModel();
        authModel.setScopes(Arrays.asList("user", "repo", "gist", "notifications", "read:org"));
        authModel.setNote("Test");
        authModel.setClientId(CLIENT_ID);
        authModel.setClientSecret(CLIENT_SECRET);
        authModel.setNoteUrl("");
        return authModel;
    }
}
