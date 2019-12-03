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

import static com.example.tescotomonzo.AuthConfig.CLIENT_ID;
import static com.example.tescotomonzo.AuthConfig.CLIENT_SECRET;
import static com.example.tescotomonzo.AuthConfig.REDIRECT_URI;
import static com.example.tescotomonzo.GeneralConfig.ACCESS_TOKEN_URL;
import static com.example.tescotomonzo.GeneralConfig.LIST_POTS_URL;
import static com.example.tescotomonzo.GeneralConfig.WHO_AM_I;

public class MonzoAPI {

    private String token;
    private Access access = new Access();

    public void requestAccessToken(Context context, Boolean moneyMoves) {
        String code = access.getCode(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ACCESS_TOKEN_URL,
                requestAccess -> {
                    token = StringUtils.substringBetween(requestAccess, "access_token\":\"", "\"");
                    access.setAccessToken(context, token);
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
        String token = access.getAccessToken(context);
        StringRequest checkRequest = new StringRequest(Request.Method.GET, WHO_AM_I, checkAccess -> {
            if (checkAccess.length() < 100) {
                requestAccessToken(context, true);
            } else {
                String authentication = StringUtils.substringBetween(checkAccess, ":", ",");
                if (authentication.equals("true")) {
                    listPots(context, token);
                }}},
                error -> Log.e("error is ", "" + error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(checkRequest);
    }

    private void listPots(Context context, String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest potsRequest = new StringRequest(Request.Method.GET, LIST_POTS_URL, listPots ->
                listPots.length(),
                error -> Log.e("error is ", "" + error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(potsRequest);

    }


}
