package com.example.tescotomonzo;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MonzoRedirectActivity extends AppCompatActivity {

    private String code;
    private String state;
    private MonzoAccessToken monzoAccessToken = new MonzoAccessToken();

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
            monzoAccessToken.requestAccessToken(code, this);
        }
    }
}