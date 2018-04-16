package com.bobrov.mobilegithubclient.Retrofit;

import com.bobrov.mobilegithubclient.AuthModel;
import com.bobrov.mobilegithubclient.LoginData;
import com.bobrov.mobilegithubclient.Responses.BranchResponse;
import com.bobrov.mobilegithubclient.Responses.CommitsResponse;
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
import retrofit2.http.Query;

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

   @Headers("Content-Type: application/json")
   @GET("repos/{nameOwner}/{nameRepo}/commits")
   Call<List<CommitsResponse>> getCommits(@Path("nameOwner") String nameOwner,@Path("nameRepo") String nameRepo);

   @Headers("Content-Type: application/json")
   @GET("repos/{nameOwner}/{nameRepo}/commits")
   Call<List<CommitsResponse>> getBranchCommits(@Path("nameOwner") String nameOwner,@Path("nameRepo") String nameRepo,@Query("sha") String branch);

   @Headers("Content-Type: application/json")
   @GET("repos/{nameOwner}/{nameRepo}/branches")
   Call<List<BranchResponse>> getBranches(@Path("nameOwner") String nameOwner, @Path("nameRepo") String nameRepo);

}
