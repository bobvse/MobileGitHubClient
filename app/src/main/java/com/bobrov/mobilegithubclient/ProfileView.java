package com.bobrov.mobilegithubclient;

import com.arellomobile.mvp.MvpView;
import com.bobrov.mobilegithubclient.Responses.UserResponse;

public interface ProfileView extends MvpView {

    void showProgress();

    void hideProgress();

    void setDataIntoView(UserResponse userResponse);

    void showError(String error);

    void startCommitsActivity();
}
