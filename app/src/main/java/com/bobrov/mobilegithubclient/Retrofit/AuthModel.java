package com.bobrov.mobilegithubclient.Retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 25.03.2018.
 */

public class AuthModel implements Serializable {

    @SerializedName("clientId")
    private String clientId;
    @SerializedName("clientSecret")
    private String clientSecret;
    @SerializedName("scopes")
    private List<String> scopes = new ArrayList<>();
    @SerializedName("note")
    private String note;
    @SerializedName("noteUrl")
    private String noteUrl;
    private String field;
    private String description;

    public AuthModel(){

    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public List<String> getScopes() {
        return scopes;
    }


    public String getNote() {
        return note;
    }

    public String getNoteUrl() {
        return noteUrl;
    }
}
