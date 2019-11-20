package com.example.tescotomonzo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.tescotomonzo.Config.ACCESS_TOKEN_URL;
import static com.example.tescotomonzo.Config.CLIENT_ID;
import static com.example.tescotomonzo.Config.CLIENT_SECRET;
import static com.example.tescotomonzo.Config.REDIRECT_URI;

public class MonzoAccessToken {

    private String monzo_token_url = ACCESS_TOKEN_URL;
    private String access_token;
    private String expires_in;

    public void requestAccessToken(String code, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, monzo_token_url,
                response -> {
                    access_token = StringUtils.substringBetween(response, "access_token\":\"", "\"");
                    Toast.makeText(context, "Access token activated", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(context,"Error, something fucked up", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new LinkedHashMap<>();
                params.put("grant_type", "authorization_code");
                params.put("client_id", CLIENT_ID);
                params.put("client_secret", CLIENT_SECRET);
                params.put("redirect_uri", REDIRECT_URI);
                params.put("code", code);

                return params;
            }
        };
        queue.add(postRequest);

    }

}
