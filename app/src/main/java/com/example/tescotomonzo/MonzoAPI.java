package com.example.tescotomonzo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.tescotomonzo.Config.ACCESS_TOKEN_URL;
import static com.example.tescotomonzo.Config.CLIENT_ID;
import static com.example.tescotomonzo.Config.CLIENT_SECRET;
import static com.example.tescotomonzo.Config.LIST_POTS_URL;
import static com.example.tescotomonzo.Config.REDIRECT_URI;
import static com.example.tescotomonzo.Config.WHO_AM_I;

public class MonzoAPI {

    private String monzo_token_url = ACCESS_TOKEN_URL;
    private String token;
    AccessToken accessToken = new AccessToken();

    public void requestAccessToken(String code, Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, monzo_token_url,
                requestAccess -> {
                    token = StringUtils.substringBetween(requestAccess, "access_token\":\"", "\"");
                    accessToken.setAccessToken(token);
                    Toast.makeText(context, "Access token activated", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(context, "Error, something fucked up", Toast.LENGTH_SHORT).show();
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

    public void checkAccessToken(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String token = accessToken.getAccessToken();
        StringRequest request = new StringRequest(Request.Method.POST, WHO_AM_I, checkAccess -> {
            Log.d("CHECKACCESS", checkAccess);
        },
                error -> Log.e("error is ", "" + error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorisation", token);
                return params;
            }
        };
        queue.add(request);
    }

    public void listPots(Context context) {
        AccessToken accessToken = new AccessToken();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, LIST_POTS_URL, listPots ->
                Log.e("List pots", listPots),
                error -> Log.e("error is ", "" + error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorisation", accessToken.getAccessToken());
                return params;
            }
        };
        queue.add(request);

    }


}
