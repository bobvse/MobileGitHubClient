package com.bobrov.mobilegithubclient.Retrofit;

import com.bobrov.mobilegithubclient.AuthModel;
import com.bobrov.mobilegithubclient.LoginBasicActivity;
import com.bobrov.mobilegithubclient.LoginData;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Vladimir on 16.03.2018.
 */

public interface GitHubApi {

   @Headers("Content-Type: application/json")
   @POST("authorizations")
   Call<LoginData> doLogin(@Body AuthModel authModel);


}
