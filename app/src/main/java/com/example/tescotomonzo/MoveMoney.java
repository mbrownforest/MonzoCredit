package com.example.tescotomonzo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

public class MoveMoney extends AsyncTask<Context, Integer, Void> {

    MonzoAPI monzoAPI = new MonzoAPI();
    private Context context;
    private Integer inte;

    public MoveMoney(Context context, Integer inte, Void voidd) {
        this.context = context;
    }

    protected void onPostExecute(Void result) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> monzoAPI.listPots(context), 100000);
    }


    @Override
    protected Void doInBackground(Context... contexts) {
        this.context = contexts[0];
        monzoAPI.requestAccessToken(contexts[0]);
        return null;
    }
}
