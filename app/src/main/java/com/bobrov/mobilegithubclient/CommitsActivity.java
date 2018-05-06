package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bobrov.mobilegithubclient.Adapters.BranchesListAdapter;
import com.bobrov.mobilegithubclient.Adapters.CommitsListAdapter;
import com.bobrov.mobilegithubclient.Responses.BranchResponse;
import com.bobrov.mobilegithubclient.Responses.CommitsResponse;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;

import java.util.List;

public class CommitsActivity extends MvpAppCompatActivity implements CommitsView {

    private CommitsListAdapter commitsListAdapter;
    private BranchesListAdapter branchesListAdapter;

    private RelativeLayout progress;
    private ListView commitsListView;
    private TextView commitsCount;
    private Spinner checkBranch;

    private ReposResponse currentRepo;

    @InjectPresenter
    CommitsPresenter commitsPresenter;

    @ProvidePresenter
    public CommitsPresenter commitsPresenter() {
        return new CommitsPresenter(getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commits_activity);
        initComponents();

        getData();

        checkBranchesCommits();
    }

    private void initComponents(){
        commitsCount = findViewById(R.id.commit_count_tv);
        checkBranch = findViewById(R.id.branch_spinner);
        commitsListView = findViewById(R.id.commits_list_view);

        progress = findViewById(R.id.commits_list_relative_progress);
        progress.setVisibility(RelativeLayout.GONE);

        branchesListAdapter = new BranchesListAdapter(this);
        commitsListAdapter = new CommitsListAdapter(this);

        checkBranch.setAdapter(branchesListAdapter);
        commitsListView.setAdapter(commitsListAdapter);
    }

    private void getData() {
        showProgress();
        currentRepo = (ReposResponse) getIntent().getSerializableExtra(ProfileActivity.EXTRA_REPOSITORY_KEY);
        commitsPresenter.loadBranches(currentRepo);
    }


    private void checkBranchesCommits() {
        checkBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                BranchResponse br1 = (BranchResponse) branchesListAdapter.getItem(position);
                commitsPresenter.loadCommitsBranches(br1.getCommit().getSha());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    @Override
    public void showProgress() {
        progress.setVisibility(RelativeLayout.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(RelativeLayout.GONE);
    }

    @Override
    public void showBranches(List<BranchResponse> branches) {
        branchesListAdapter.setData(branches);
    }

    @Override
    public void showCommitsList(List<CommitsResponse> commitsResponses) {
        commitsListAdapter.setData(commitsResponses);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showCountCommits(int count) {
        //todo e+s
        commitsCount.setText(count + " commits");
    }
}
