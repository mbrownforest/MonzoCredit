package com.example.tescotomonzo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import static android.Manifest.permission_group.STORAGE;

public class NotificationBalance implements Serializable {

    private static final String AMEX_BALANCE = "amex_balance";
    private static final String AMEX_CHARGE = "amex_charge";
    private static final String TESCO_CHARGE = "tesco_charge";

    public String getAmexBalance(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(AMEX_BALANCE, null);
    }

    public void setAmexBalance(Context context, String amexBalance) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(AMEX_BALANCE, amexBalance);
        editor.apply();
    }

    public String getAmexCharge(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(AMEX_CHARGE, null);
    }

    public void setAmexCharge(Context context, String amexCharge) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(AMEX_CHARGE, amexCharge);
        editor.apply();
    }

    public String getTescoCharge(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(TESCO_CHARGE, null);
    }

    public void setTescoCharge(Context context, String tescoCharge) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TESCO_CHARGE, tescoCharge);
        editor.apply();
    }
}
