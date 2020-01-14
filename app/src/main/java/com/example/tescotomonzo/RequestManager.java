package com.example.tescotomonzo;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.util.Map;

import static com.example.tescotomonzo.GeneralConfig.ACCESS_TOKEN_URL;

public class RequestManager {

    private static RequestManager instance = null;
    private RequestQueue requestQueue;

    private RequestManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized RequestManager getInstance(Context context) {
        if (null == instance)
            instance = new RequestManager(context);
        return instance;
    }

    public static synchronized RequestManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void postRequest(String url, Map<String, String> requestParams, final RequestListener<String> listener) {
        JSONObject jsonObject = new JSONObject(requestParams);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ACCESS_TOKEN_URL, jsonObject,
                response -> listener.getResult(response.toString()),
                error -> Log.d("REQUEST_MANAGER_ERROR", error.toString()));
        requestQueue.add(request);
    }
}
