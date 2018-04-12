package com.bobrov.mobilegithubclient.Retrofit;

import com.bobrov.mobilegithubclient.AuthModel;
import com.bobrov.mobilegithubclient.LoginData;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;
import com.bobrov.mobilegithubclient.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vladimir on 16.03.2018.
 */

public interface GitHubApi {

   @Headers("Content-Type: application/json")
   @POST("authorizations")
   Call<LoginData> doLogin(@Body AuthModel authModel);

   @Headers("Content-Type: application/json")
   @GET("user")
   Call<UserResponse> getUser();

   @Headers("Content-Type: application/json")
   @DELETE("authorizations/{id}")
   Call<Object> deleteSession(@Path("id") String userId);

   @Headers("Content-Type: application/json")
   @GET("user/repos")
   Call<List<ReposResponse>> getRepos();
}
