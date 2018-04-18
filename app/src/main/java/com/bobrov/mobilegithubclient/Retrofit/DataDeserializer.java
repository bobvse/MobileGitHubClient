package com.bobrov.mobilegithubclient.Retrofit;

import com.bobrov.mobilegithubclient.Responses.AuthorAndCommitter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataDeserializer implements JsonDeserializer<AuthorAndCommitter> {
    @Override
    public AuthorAndCommitter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        AuthorAndCommitter data = new AuthorAndCommitter();
            data.setName(jsonObject.get("name").getAsString());
            data.setEmail(jsonObject.get("email").getAsString());
            String date = (jsonObject.get("date").getAsString());

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = f.parse(date);
            long milliseconds = d.getTime();
            data.setDate(milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
