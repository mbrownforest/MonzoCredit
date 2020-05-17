package com.example.tescotomonzo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import static android.Manifest.permission_group.STORAGE;

class AuthConfig implements Serializable {

   static final String REDIRECT_URI = "https://tescotomonzo.example.com/oauth/callback/";
   private static final String CLIENT_ID = "client_id";
   private static final String CLIENT_SECRET = "client_secret";

   public String getClientId(Context context) {
      SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
      return sharedPref.getString(CLIENT_ID, null);
   }

   public void setClientId(Context context, String clientId) {
      SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putString(CLIENT_ID, clientId);
      editor.apply();
   }

   public String getClientSecret(Context context) {
      SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
      return sharedPref.getString(CLIENT_SECRET, null);
   }

   public void setClientSecret(Context context, String clientSecret) {
      SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putString(CLIENT_SECRET, clientSecret);
      editor.apply();
   }
}

