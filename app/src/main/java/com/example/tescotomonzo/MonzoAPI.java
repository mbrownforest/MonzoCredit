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

import static com.example.tescotomonzo.AuthConfig.*;
import static com.example.tescotomonzo.GeneralConfig.*;

public class MonzoAPI {

    private String monzo_token_url = ACCESS_TOKEN_URL;
    private String token;
    private Access access = new Access();

    public void requestAccessToken(Context context) {
        String code = access.getCode(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, monzo_token_url,
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
        StringRequest request = new StringRequest(Request.Method.POST, WHO_AM_I, checkAccess -> {
            Log.d("CHECKACCESS", checkAccess);
            if(checkAccess != null){
                listPots(context);
            } else {
                requestAccessToken(context);
            }
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
        String token = access.getAccessToken(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, LIST_POTS_URL, listPots ->
                Log.e("List pots", listPots),
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


}
