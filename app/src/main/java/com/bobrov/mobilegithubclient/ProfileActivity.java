package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bobrov.mobilegithubclient.Adapters.ReposListAdapter;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.Responses.UserResponse;
import com.bobrov.mobilegithubclient.Retrofit.GitHubApi;

import java.util.ArrayList;
import java.util.List;

import static com.bobrov.mobilegithubclient.LoginBasicActivity.MY_SETTINGS;

public class ProfileActivity extends MvpAppCompatActivity implements ProfileView, View.OnClickListener, AdapterView.OnItemClickListener {
    public static String EXTRA_REPOSITORY_KEY = "repo_key";

    private TextView nameTV;
    private RelativeLayout progress;
    private ReposListAdapter reposListAdapter;

    @InjectPresenter
    ProfilePresenter profilePresenter;

    @ProvidePresenter
    public ProfilePresenter providePresenter() {
        return new ProfilePresenter(getSharedPreferences(LoginBasicActivity.MY_SETTINGS, Context.MODE_PRIVATE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        initComponents();

        profilePresenter.loadUserData();
        profilePresenter.loadRepos();
    }

    private void initComponents() {
        findViewById(R.id.profile_activity_logout_button).setOnClickListener(this);

        nameTV = findViewById(R.id.profile_activity_name_texView);
        progress = findViewById(R.id.profile_relative_progress);
        progress.setVisibility(RelativeLayout.GONE);

        ListView reposListView = findViewById(R.id.repos_list_view);
        reposListAdapter = new ReposListAdapter(this);
        reposListView.setAdapter(reposListAdapter);
        reposListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_activity_logout_button:
                showProgress();
                profilePresenter.deleteSession();
                break;
        }
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CommitsActivity.class);
        intent.putExtra(EXTRA_REPOSITORY_KEY, (ReposResponse) reposListAdapter.getItem(position));

        startActivity(intent);
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
    public void setDataIntoView(UserResponse userResponse) {
        nameTV.setText("Здравствуйте, " + userResponse.getUsername());
    }

    @Override
    public void showReposList(List<ReposResponse> reposList) {
        reposListAdapter.setData(reposList);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
    }
}
