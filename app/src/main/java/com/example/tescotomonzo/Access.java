package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class Access implements Serializable {

    private String accessToken;
    private static final String STORAGE = "AuthData";
    private static final String ACCESS_CODE = "access_code";

    public String getCode(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(ACCESS_CODE, null);
    }

    public void setCode(Context context, String code) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_CODE, code);
        editor.apply();
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

}
