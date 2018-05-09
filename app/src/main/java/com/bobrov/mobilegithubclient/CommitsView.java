package com.bobrov.mobilegithubclient;

import com.arellomobile.mvp.MvpView;
import com.bobrov.mobilegithubclient.Responses.BranchResponse;
import com.bobrov.mobilegithubclient.Responses.CommitsResponse;

import java.util.List;

public interface CommitsView extends MvpView {
    void showProgress();

    void hideProgress();

    void showBranches(List<BranchResponse> branches);

    void showCommitsList(List<CommitsResponse> commitsResponses);

    void showError(String error);

    void showCountCommits(int count);

}
