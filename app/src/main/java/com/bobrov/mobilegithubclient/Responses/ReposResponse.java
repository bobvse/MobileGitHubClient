package com.bobrov.mobilegithubclient.Responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReposResponse implements Serializable {
    private String id;
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private OwnerResponse owner;
    @SerializedName("private")
    private String isPrivate;
    @SerializedName("html_url")
    private String htmlUrl;
    private String description;
    private String fork;
    private String url;
    @SerializedName("stargazers_count")
    private int stars;
    //остюда дергать списоккоммитов общий
    @SerializedName("svn_url")
    private String svnUrl;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public OwnerResponse getOwner() {
        return owner;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getFork() {
        return fork;
    }

    public String getUrl() {
        return url;
    }

    public int getStars() {
        return stars;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    private static class Premissions implements Serializable {
        private boolean admin;
        private boolean push;
        private boolean pull;
    }

}
