package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.bobrov.mobilegithubclient.Retrofit.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bobrov.mobilegithubclient.LoginBasicActivity.MY_SETTINGS;

public class ProfileActivity extends MvpAppCompatActivity implements ProfileView, View.OnClickListener, AdapterView.OnItemClickListener {
    public static String EXTRA_REPOSITORY_KEY = "repo_key";

    SharedPreferences sp;
    private TextView nameTV;
    private GitHubApi api;

    private RelativeLayout progress;

    private List<ReposResponse> repos = new ArrayList<>();
    private ReposListAdapter reposListAdapter;

    @InjectPresenter
    ProfilePresenter profilePresenter;

    @ProvidePresenter
    public ProfilePresenter providePresenter() {
        return new ProfilePresenter(getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        initComponents();
        profilePresenter.loadUserData();
        loadRepos();
    }

    private void initComponents() {
        sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        findViewById(R.id.profile_activity_logout_button).setOnClickListener(this);

        nameTV = findViewById(R.id.profile_activity_name_texView);
        progress = findViewById(R.id.profile_relative_progress);
        progress.setVisibility(RelativeLayout.GONE);

        ListView reposListView = findViewById(R.id.repos_list_view);
        reposListAdapter = new ReposListAdapter(this);
        reposListView.setAdapter(reposListAdapter);
        reposListView.setOnItemClickListener(this);
    }

    private void loadRepos() {
        String token = sp.getString("Token", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.getRepos().enqueue(new Callback<List<ReposResponse>>() {
            @Override
            public void onResponse(Call<List<ReposResponse>> call, Response<List<ReposResponse>> response) {
                repos = response.body();
                reposListAdapter.setData(repos);
            }

            @Override
            public void onFailure(Call<List<ReposResponse>> call, Throwable t) {

            }

        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_activity_logout_button:
                deleteSession();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CommitsActivity.class);
        intent.putExtra(EXTRA_REPOSITORY_KEY, (ReposResponse) reposListAdapter.getItem(position));
        startActivity(intent);
    }

    private void deleteSession() {
        progress.setVisibility(RelativeLayout.VISIBLE);
        String id = sp.getString("id", null);
        String token = sp.getString("BasicToken", null);
        api = RetrofitSingleton.getInstance().init(token).create(GitHubApi.class);
        api.deleteSession(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Integer i = response.code();
                if (i == 204) {
                    clearToken();
                    Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                progress.setVisibility(RelativeLayout.GONE);
                String i2 = t.getMessage();
                Toast.makeText(getApplicationContext(), i2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearToken() {
        SharedPreferences.Editor e = sp.edit();
        e.remove("Token");
        e.remove("id");
        e.apply();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDataIntoView(UserResponse userResponse) {
        nameTV.setText("Здравствуйте, " + userResponse.getUsername());
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void startCommitsActivity() {

    }
}
