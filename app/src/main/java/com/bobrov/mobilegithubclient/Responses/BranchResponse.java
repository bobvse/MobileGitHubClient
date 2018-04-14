package com.bobrov.mobilegithubclient.Responses;

import java.io.Serializable;

public class BranchResponse implements Serializable {
    private String name;
    private CommitInBranch commit;

    public String getName() {
        return name;
    }

    public CommitInBranch getCommit() {
        return commit;
    }

    private static class CommitInBranch implements Serializable {
        private String sha;
        private String url;

        public String getSha() {
            return sha;
        }

        public String getUrl() {
            return url;
        }
    }
}
