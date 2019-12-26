package com.example.tescotomonzo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import static android.Manifest.permission_group.STORAGE;

public class AmexBalance implements Serializable {

    private static final String AMEX_BALANCE = "amex_balance";

    public Float getAmexBalance(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getFloat(AMEX_BALANCE, 0f);
    }

    public void setAmexBalance(Context context, Float amexBalance) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(AMEX_BALANCE, amexBalance);
        editor.apply();
    }
}
