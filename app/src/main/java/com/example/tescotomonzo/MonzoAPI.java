package com.example.tescotomonzo;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
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
import java.util.Objects;

import static com.example.tescotomonzo.AuthConfig.REDIRECT_URI;
import static com.example.tescotomonzo.GeneralConfig.ACCESS_TOKEN_URL;
import static com.example.tescotomonzo.GeneralConfig.ACCOUNT_URL;
import static com.example.tescotomonzo.GeneralConfig.BALANCE_URL;
import static com.example.tescotomonzo.GeneralConfig.DEPOSIT_URL;
import static com.example.tescotomonzo.GeneralConfig.LIST_POTS_URL;
import static com.example.tescotomonzo.GeneralConfig.WHO_AM_I;

public class MonzoAPI {

    private String token;
    private String returnRefreshToken;
    private String accountId;
    private Access access = new Access();
    private NotificationBalance notificationBalance = new NotificationBalance();
    private Map<String, Pots> potsMap = new HashMap<>();
    private List<Account> accountsArray = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();
    private String dedupeId;

    void requestAccessToken(Context context) {
        AuthConfig authConfig = new AuthConfig();
        String code = access.getCode(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ACCESS_TOKEN_URL,
                requestAccess -> {
                    token = StringUtils.substringBetween(requestAccess, "access_token\":\"", "\"");
                    returnRefreshToken = StringUtils.substringBetween(requestAccess, "refresh_token\":\"", "\"");
                    access.setAccessToken(context, token);
                    access.setRefreshToken(context, returnRefreshToken);
                },
                error -> Log.d("Error.Response", error.toString())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new LinkedHashMap<>();
                params.put("grant_type", "authorization_code");
                params.put("client_id", authConfig.getClientId(context));
                params.put("client_secret", authConfig.getClientSecret(context));
                params.put("redirect_uri", REDIRECT_URI);
                params.put("code", code);

                return params;
            }
        };
        queue.add(postRequest);
    }

    void checkAccessToken(Context context, String dedupe) {
        RequestQueue queue = Volley.newRequestQueue(context);
        this.dedupeId = dedupe;
        token = access.getAccessToken(context);
        StringRequest checkRequest = new StringRequest(Request.Method.GET, WHO_AM_I, checkAccess -> {
            String authentication = StringUtils.substringBetween(checkAccess, ":", ",");
            if (authentication.equals("true")) {
                listPots(context);
            }
        }, error -> showErrorAndRefreshToken(error, context)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(checkRequest);
    }

    private void showErrorAndRefreshToken(VolleyError error, Context context) {
        String refreshToken = access.getRefreshToken(context);
        if (refreshToken != null) {
            refreshAccessToken(context, refreshToken);
        } else {
            requestAccessToken(context);
        }
    }

    private void refreshAccessToken(Context context, String refreshToken) {
        RequestQueue queue = Volley.newRequestQueue(context);
        AuthConfig authConfig = new AuthConfig();
        StringRequest refreshRequest = new StringRequest(Request.Method.POST, ACCESS_TOKEN_URL,
                requestAccess -> {
                    token = StringUtils.substringBetween(requestAccess, "access_token\":\"", "\"");
                    returnRefreshToken = StringUtils.substringBetween(requestAccess, "refresh_token\":\"", "\"");
                    access.setAccessToken(context, token);
                    access.setRefreshToken(context, returnRefreshToken);
                    listPots(context);
                },
                error -> Log.d("Error.Response", error.toString())

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new LinkedHashMap<>();
                params.put("grant_type", "refresh_token");
                params.put("client_id", authConfig.getClientId(context));
                params.put("client_secret", authConfig.getClientSecret(context));
                params.put("refresh_token", refreshToken);

                return params;
            }
        };
        queue.add(refreshRequest);
    }

    private void listPots(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest potsRequest = new StringRequest(Request.Method.GET, LIST_POTS_URL,
                pots -> mapPots(pots, context),
                Throwable::printStackTrace) {
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
                Throwable::printStackTrace) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(accountsRequest);

    }

    private void listBalance(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String balanceUrl = BALANCE_URL + "account_id=" + accountId;
        StringRequest balanceRequest = new StringRequest(Request.Method.GET, balanceUrl,
                balance -> mapBalance(balance, context),
                Throwable::printStackTrace) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(balanceRequest);
    }

    private void mapBalance(String balanceAmount, Context context) {
        try {
            JSONObject jsonObj = new JSONObject(balanceAmount);
            Balance balance = mapper.readValue(StringUtils.substringBefore(jsonObj.toString(), ",\"balance_i") + "}", Balance.class);
            if (balance != null) {
                checkMovement(balance, context);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void depositMoneyIntoPot(Context context, String amount, String potId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String depositUrl = DEPOSIT_URL + potId + "/deposit";
        StringRequest amexDepositRequest = new StringRequest(Request.Method.PUT, depositUrl,
                transfer -> checkPotFilled(context, amount, potId),
                error -> Log.d("ERROR", error.toString())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("source_account_id", accountId);
                params.put("amount", amount);
                params.put("dedupe_id", dedupeId);
                return params;
            }
        };
        queue.add(amexDepositRequest);
    }

    private void checkPotFilled(Context context, String balanceTransferred, String potId) {
        Log.d("IT WORKED", balanceTransferred);
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
                    if (userAccount != null) {
                        accountId = userAccount.id;
                        listBalance(context);
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
                Pots pot = mapper.readValue(
                        StringUtils.substringBetween(ja_data.get(i).toString(), "", ",\"currency") + "}",
                        Pots.class);
                potsMap.put(pot.name, pot);
            }
            if (!potsMap.isEmpty()) {
                listAccount(context);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void checkMovement(Balance balance, Context context) {
        UserCreditValues userCreditValues = new UserCreditValues();
        String mainAccountBalance = balance.getBalance().toString();
        Float mainAccountBal = Float.valueOf(mainAccountBalance.substring(0, mainAccountBalance.length() - 2) + "." + mainAccountBalance.substring(mainAccountBalance.length() - 2));
        String amexCharge = notificationBalance.getAmexCharge(context);
        String monzoPotName = userCreditValues.getMonzoPotName(context);

        if (checkEnoughMoneyInAccount(mainAccountBal, amexCharge)) {
            depositMoneyIntoPot(context, amexCharge.replace(".", ""), Objects.requireNonNull(potsMap.get(monzoPotName)).id);
        }

    }

    private boolean checkEnoughMoneyInAccount(Float mainAccountBal, String charge) {
        return mainAccountBal >= Float.parseFloat(charge);
    }

}
