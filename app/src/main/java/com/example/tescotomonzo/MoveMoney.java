package com.example.tescotomonzo;

import android.content.Context;

public class MoveMoney {

    MonzoAPI monzoAPI = new MonzoAPI();

    public void makeMoneyMoves(Context context) {
        AccessToken accessToken = new AccessToken();
        monzoAPI.requestAccessToken(accessToken.getCode(), context);
    }

}
