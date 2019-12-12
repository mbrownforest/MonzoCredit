package com.example.tescotomonzo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class Access implements Serializable {

    private static final String STORAGE = "AuthData";
    private static final String ACCESS_CODE = "access_code";
    private static final String ACCESS_TOKEN = "access_token";

    public String getCode(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(ACCESS_CODE, null);
    }

    public void setCode(Context context, String code) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_CODE, code);
        editor.apply();
    }

    public void setAccessToken(Context context, String accessToken) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public String getAccessToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(ACCESS_TOKEN, null);
    }

}
