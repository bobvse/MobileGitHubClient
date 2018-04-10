package com.bobrov.mobilegithubclient.Responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Commit implements Serializable {
    private String message;
    private String url;
    @SerializedName("comment_count")
    private String commetCount;
    private AuthorAndCommiter author;
    private AuthorAndCommiter comitter;
    private  Tree tree;
    private Verification verification;

    private static class Tree implements Serializable {
        private String sha;
        private String url;
    }

    private static class Verification implements Serializable {
        private String verified;
        private String reason;
        private String signature;
        private String payload;
    }

}
