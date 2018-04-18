package com.bobrov.mobilegithubclient.Responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Commit implements Serializable {
    private String message;
    private String url;
    @SerializedName("comment_count")
    private String commetCount;
    private AuthorAndCommitter author;
    private AuthorAndCommitter committer;
    private Tree tree;
    private Verification verification;

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getCommetCount() {
        return commetCount;
    }

    public AuthorAndCommitter getAuthor() {
        return author;
    }

    public AuthorAndCommitter getCommitter() {
        return committer;
    }

    public Tree getTree() {
        return tree;
    }

    public Verification getVerification() {
        return verification;
    }

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
