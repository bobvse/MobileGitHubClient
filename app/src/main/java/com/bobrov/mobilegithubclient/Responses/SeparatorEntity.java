package com.bobrov.mobilegithubclient.Responses;

public class SeparatorEntity extends Entity {
    private long date;

    public SeparatorEntity(){}

    public SeparatorEntity(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType(){
        return Entity.SEPARATOR_TYPE;
    }
}
