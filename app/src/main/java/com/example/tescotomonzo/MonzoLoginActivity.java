package com.example.tescotomonzo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.tescotomonzo.AuthConfig.CLIENT_ID;
import static com.example.tescotomonzo.AuthConfig.REDIRECT_URI;
import static com.example.tescotomonzo.GeneralConfig.REDIRECT_MONZO;

public class MonzoLoginActivity extends AppCompatActivity {

    private Map<String, String> params = new LinkedHashMap<>();
    private String redirect_monzo = REDIRECT_MONZO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("response_type", "code");
        //sort this state situation out
        params.put("state", "rasdaosijfoihliuhkuhbku");
        try {
            redirect_monzo += ParameterStringBuilder.getParamsString(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(redirect_monzo));
        startActivity(viewIntent);

    }

}
