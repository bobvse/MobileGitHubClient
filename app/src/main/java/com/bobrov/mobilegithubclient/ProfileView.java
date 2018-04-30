package com.bobrov.mobilegithubclient;

import com.arellomobile.mvp.MvpView;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Responses.UserResponse;

import java.util.List;

public interface ProfileView extends MvpView {

    void showProgress();

    void hideProgress();

    void setDataIntoView(UserResponse userResponse);

    void showReposList(List<ReposResponse> reposList);

    void showError(String error);

    void startLoginActivity();
}
