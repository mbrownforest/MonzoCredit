package com.example.tescotomonzo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

class Access implements Serializable {

    private static final String STORAGE = "AuthData";
    private static final String ACCESS_CODE = "access_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String ACCESS_STATE = "access_state";
    private static final String REFRESH_TOKEN = "refresh_token";

    String getCode(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(ACCESS_CODE, null);
    }

    void setCode(Context context, String code) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_CODE, code);
        editor.apply();
    }

    String getState(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(ACCESS_STATE, null);
    }

    void setState(Context context, String state) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_STATE, state);
        editor.apply();
    }

    void setAccessToken(Context context, String accessToken) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    String getAccessToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(ACCESS_TOKEN, null);
    }

    void setRefreshToken(Context context, String refreshToken) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    String getRefreshToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(REFRESH_TOKEN, null);
    }
}
