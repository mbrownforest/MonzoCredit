package com.example.tescotomonzo;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MonzoRedirectActivity extends AppCompatActivity {

    private String code;
    private String state;
    private MonzoAPI monzoAPI = new MonzoAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri URIdata = getIntent().getData();
        if (URIdata != null) {
            code = URIdata.getQueryParameter("code");
            state = URIdata.getQueryParameter("state");
        }

        //TODO check state
        if (code != null) {
            AccessToken accessToken = new AccessToken();
            accessToken.setCode(code);
            monzoAPI.requestAccessToken(code, this);
        }
    }
}
