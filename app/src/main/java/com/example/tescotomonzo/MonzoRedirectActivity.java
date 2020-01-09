package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MonzoRedirectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonzoAPI monzoAPI = new MonzoAPI();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        Access access = new Access();
        Uri URIdata = getIntent().getData();
        if (URIdata != null) {
            String code = URIdata.getQueryParameter("code");
            String state = URIdata.getQueryParameter("state");
            if (!access.getState(this).equals(state)) {
                code = null;
            } else if (code != null) {
                access.setCode(this, code);
                monzoAPI.requestAccessToken(this);
            }
        }

    }
}
