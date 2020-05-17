package com.example.tescotomonzo;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class UserCreditValues implements Serializable {

    private static final String STORAGE = "UserCreditValues";
    private static final String MONZO_POT_NAME = "monzo_pot_name";
    private static final String CREDIT_CARD_NOTIFICATION = "credit_card_notification";

    public String getMonzoPotName(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(MONZO_POT_NAME, null);
    }

    public void setMonzoPotName(Context context, String monzoPotName) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(MONZO_POT_NAME, monzoPotName);
        editor.apply();
    }

    public String getCreditCardNotification(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(CREDIT_CARD_NOTIFICATION, null);
    }

    public void setCreditCardNotification(Context context, String creditCardNotification) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CREDIT_CARD_NOTIFICATION, creditCardNotification);
        editor.apply();
    }
}
