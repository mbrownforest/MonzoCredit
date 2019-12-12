package com.example.tescotomonzo;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.tescotomonzo.AuthConfig.CLIENT_ID;
import static com.example.tescotomonzo.AuthConfig.CLIENT_SECRET;
import static com.example.tescotomonzo.AuthConfig.REDIRECT_URI;
import static com.example.tescotomonzo.GeneralConfig.ACCESS_TOKEN_URL;
import static com.example.tescotomonzo.GeneralConfig.ACCOUNT_URL;
import static com.example.tescotomonzo.GeneralConfig.BALANCE_URL;
import static com.example.tescotomonzo.GeneralConfig.LIST_POTS_URL;
import static com.example.tescotomonzo.GeneralConfig.WHO_AM_I;

public class MonzoAPI {

    private String token;
    private Access access = new Access();
    private Balances balances = new Balances();
    private List<Pots> potsArray = new ArrayList<>();
    private List<Account> accountsArray = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();

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
        token = access.getAccessToken(context);
        StringRequest checkRequest = new StringRequest(Request.Method.GET, WHO_AM_I, checkAccess -> {
            if (checkAccess.length() < 100) {
                requestAccessToken(context, true);
            } else {
                String authentication = StringUtils.substringBetween(checkAccess, ":", ",");
                if (authentication.equals("true")) {
                    listPots(context, token);
                }
            }
        },
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
        StringRequest potsRequest = new StringRequest(Request.Method.GET, LIST_POTS_URL,
                pots -> mapPots(pots, context),
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

    private void listAccount(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest accountsRequest = new StringRequest(Request.Method.GET, ACCOUNT_URL,
                account -> mapAccounts(account, context),
                error -> Log.e("error is ", "" + error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(accountsRequest);

    }

    private void listBalance(Context context, String id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest balanceRequest = new StringRequest(Request.Method.POST, BALANCE_URL,
                balance -> mapBalance(balance, context),
                error -> Log.e("error is ", "" + error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new LinkedHashMap<>();
                params.put("account_id=", id);
                return params;
            }
        };
        queue.add(balanceRequest);

    }

    private void mapBalance(String balance, Context context) {
        Log.d("map","balanc");
    }

    private void mapAccounts(String account, Context context) {

        try {
            JSONObject jsonObj = new JSONObject(account);
            JSONArray jab_data = jsonObj.getJSONArray("accounts");

            for (int i = 0; i < jab_data.length(); i++) {
                accountsArray.add(mapper.readValue(
                        StringUtils.substringBetween(jab_data.get(i).toString(), "", ",\"currency") + "}",
                        Account.class));
            }
            if (!accountsArray.isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Account userAccount = accountsArray.stream().filter(user -> user.type.equals("uk_retail")).findFirst().orElse(null);
                    if(userAccount != null){
                        listBalance(context, userAccount.id);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void mapPots(String pots, Context context) {
        try {
            JSONObject jsonObj = new JSONObject(pots);
            JSONArray ja_data = jsonObj.getJSONArray("pots");

            for (int i = 0; i < ja_data.length(); i++) {
                potsArray.add(mapper.readValue(
                        StringUtils.substringBetween(ja_data.get(i).toString(), "", ",\"currency") + "}",
                        Pots.class));
            }
            if (!potsArray.isEmpty()) {
                checkBalanceEqual(potsArray, context);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void checkBalanceEqual(List<Pots> potList, Context context) {
        Pots amexPot = null;
        Float amexBal = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            amexPot = potList.stream().filter(pot -> pot.name.equals("AMEX")).findFirst().orElse(null);
        }
        if (amexPot != null) {
            String amexBalance = amexPot.balance.toString();
            if (amexPot.balance > 99) {
                amexBal = Float.valueOf(amexBalance.substring(0, amexBalance.length() - 2) + "." + amexBalance.substring(amexBalance.length() - 2));
            }
            if (amexBal != balances.getAmexBalance(context)) {
                listAccount(context);
                Log.d("Time to move ze money", "MMM");
            }
        }
    }

}
