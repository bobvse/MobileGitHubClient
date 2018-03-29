package com.bobrov.mobilegithubclient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vladimir on 25.03.2018.
 */

public class LoginData implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;
    private String token;
    @SerializedName("hashed_token")
    private String hashedToken;
    @SerializedName("token_last_eight")
    private String tokenLastEight;
    private String note;
    private List<String> scopes;


    LoginData() {

    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public String getHashedToken() {
        return hashedToken;
    }

    public String getTokenLastEight() {
        return tokenLastEight;
    }

    public String getNote() {
        return note;
    }

    public List<String> getScopes() {
        return scopes;
    }
}
