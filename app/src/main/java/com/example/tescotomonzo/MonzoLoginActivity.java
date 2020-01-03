package com.example.tescotomonzo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.tescotomonzo.AuthConfig.CLIENT_ID;
import static com.example.tescotomonzo.AuthConfig.REDIRECT_URI;
import static com.example.tescotomonzo.GeneralConfig.REDIRECT_MONZO;

public class MonzoLoginActivity extends AppCompatActivity {

    private Map<String, String> params = new LinkedHashMap<>();
    private String redirect_monzo = REDIRECT_MONZO;
    private Access access = new Access();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String state = getRandomState();
        access.setState(this, state);

        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("response_type", "code");
        params.put("state", state);
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

    private String getRandomState() {
        return RandomStringUtils.random(32, true, true);
    }

}
