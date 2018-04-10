package com.bobrov.mobilegithubclient.Responses;

import com.bobrov.mobilegithubclient.Responses.OwnerResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReposResponse implements Serializable {
    //TODO тут не все дописать при необходимости

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
    //остюда дергать списоккоммитов общий
    @SerializedName("svn_url")
    private String svnUrl;

    private static class Premissions implements Serializable{
        private boolean admin;
        private boolean push;
        private boolean pull;
    }

}
