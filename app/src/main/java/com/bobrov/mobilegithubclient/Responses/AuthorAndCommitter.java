package com.bobrov.mobilegithubclient.Responses;

import java.io.Serializable;

public class AuthorAndCommitter implements Serializable {
    private String name;
    private String email;
    private Long date;

    public String getName() {
        return name;
    }

    public String getEmail(String email) {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDate() {
        return date;
    }
}
