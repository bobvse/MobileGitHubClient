package com.bobrov.mobilegithubclient.Retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vladimir on 25.03.2018.
 */

public class AuthenticationInterceptor implements Interceptor {
    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        if (authToken.contains("Basic")) {
            builder.header("Authorization", authToken);
        } else {
            builder.header("Authorization", "token " + authToken);
        }
        Request request = builder.build();
        return chain.proceed(request);

    }
}
