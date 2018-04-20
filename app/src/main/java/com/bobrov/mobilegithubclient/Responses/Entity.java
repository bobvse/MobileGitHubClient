package com.bobrov.mobilegithubclient.Responses;

import android.icu.text.PluralRules;

public abstract class Entity {
    public static final int SEPARATOR_TYPE = 1;
    public static final int COMMIT_TYPE = 2;

    public abstract int getType();

}
