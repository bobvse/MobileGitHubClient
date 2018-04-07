package com.bobrov.mobilegithubclient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//TODO узнать про имена таких классов
public class UserResponse implements Serializable {
    private String id;
    @SerializedName("login")
    private String username;
    @SerializedName("public_repos")
    private String publicRepos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(String publicRepos) {
        this.publicRepos = publicRepos;
    }



}
