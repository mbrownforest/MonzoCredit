package com.example.tescotomonzo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.tescotomonzo.Config.CLIENT_ID;
import static com.example.tescotomonzo.Config.REDIRECT_MONZO;
import static com.example.tescotomonzo.Config.REDIRECT_URI;

public class MonzoLoginActivity extends AppCompatActivity {

    ParameterStringBuilder parameterStringBuilder = new ParameterStringBuilder();
    Map<String, String> params = new LinkedHashMap<>();
    String redirect_monzo = REDIRECT_MONZO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("response_type", "code");
        //sort this state situation out
        params.put("state", "rasdaosijfoihliuhkuhbku");
        try {
            redirect_monzo += parameterStringBuilder.getParamsString(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(redirect_monzo));
        startActivity(viewIntent);

    }

}
