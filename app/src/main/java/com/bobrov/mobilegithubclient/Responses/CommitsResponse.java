package com.bobrov.mobilegithubclient.Responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommitsResponse implements Serializable {
    private String sha;
    private String url;
    @SerializedName("html_url")
    private String htmlURL;
    @SerializedName("comments_url")
    private String commentsURL;
    private Commit commit;
    private OwnerResponse author;
    private OwnerResponse committer;
    private List<CommitParent> parents = new ArrayList<>();

    public String getSha() {
        return sha;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlURL() {
        return htmlURL;
    }

    public String getCommentsURL() {
        return commentsURL;
    }

    public Commit getCommit() {
        return commit;
    }

    public OwnerResponse getAuthor() {
        return author;
    }

    public OwnerResponse getCommitter() {
        return committer;
    }

    public List<CommitParent> getParents() {
        return parents;
    }


}
