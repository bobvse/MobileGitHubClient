package com.bobrov.mobilegithubclient.Responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommitParent implements Serializable {
    private String sha;
    private String url;
    @SerializedName("html_url")
    private String htmlURL;
}
