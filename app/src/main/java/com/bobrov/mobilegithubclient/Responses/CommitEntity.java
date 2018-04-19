package com.bobrov.mobilegithubclient.Responses;

public class CommitEntity extends Entity {
    private String message;
    private String author;

    public CommitEntity(){}

    public CommitEntity(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public int getType(){
        return Entity.COMMIT_TYPE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
