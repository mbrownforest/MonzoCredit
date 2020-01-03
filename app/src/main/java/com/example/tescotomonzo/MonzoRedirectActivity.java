package com.example.tescotomonzo;

import android.content.Intent;
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

        Access access = new Access();
        Uri URIdata = getIntent().getData();
        if (URIdata != null) {
            code = URIdata.getQueryParameter("code");
            state = URIdata.getQueryParameter("state");
            if (!access.getState(this).equals(state)) {
                code = null;
            } else if (code != null) {
                access.setCode(this, code);
                monzoAPI.requestAccessToken(this);
            }
        }

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
