package com.bobrov.mobilegithubclient;

import com.arellomobile.mvp.MvpView;

public interface LoginBasicView extends MvpView {

    void showProgress();

    void hideProgress();

    void showError(String error);

    void startProfile();
}
