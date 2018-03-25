package com.bobrov.mobilegithubclient;


import android.util.Base64;

import java.nio.charset.Charset;
import okio.ByteString;

import static okhttp3.internal.Util.ISO_8859_1;

/** Factory for HTTP authorization credentials. */
public final class EncodeHelper {
    private EncodeHelper() {
    }

    /** Returns an auth credential for the Basic scheme. */
    public static String basic(String userName, String password) {
        return basic(userName, password, ISO_8859_1);
    }

    public static String basic(String userName, String password, Charset charset) {
        String usernameAndPassword = userName + ":" + password;
        String encoded = ByteString.encodeString(usernameAndPassword, charset).base64();
        byte[] encodedBytes = Base64.encode(usernameAndPassword.getBytes(),0);
        return "Basic " + new String(encodedBytes);
    }
}
