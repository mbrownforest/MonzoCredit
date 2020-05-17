package com.example.tescotomonzo;

import android.content.Context;
import android.os.AsyncTask;
import android.service.notification.StatusBarNotification;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class NotificationReaderService extends AsyncTask<NotificationReaderService.MyTaskParams, Context, String> {

    private static final String WRONG_NOTIFICATION = "Null or not a monetary notification";
    Context notificationContext;
    private MonzoAPI monzoAPI;
    private NotificationBalance balances;
    private UserCreditValues userCreditValues;

    NotificationReaderService(UserCreditValues userCreditValues, NotificationBalance balances, MonzoAPI monzoAPI) {
        this.userCreditValues = userCreditValues;
        this.balances = balances;
        this.monzoAPI = monzoAPI;
    }

    protected String doInBackground(MyTaskParams... myTaskParams) {
        notificationContext = myTaskParams[0].context;
        String ticker = myTaskParams[0].sbn.getNotification().extras.getString("android.text");
        if (ticker != null && ticker.contains("£")) {
            return ticker;
        }
        return WRONG_NOTIFICATION;
    }

    protected void onPostExecute(String tickerText) {
        String dedupeId = RandomStringUtils.random(10, true, true);
        String creditCardNotification = userCreditValues.getCreditCardNotification(notificationContext);

        if (creditCardNotification != null && tickerText.contains(creditCardNotification)) {
            balances.setAmexCharge(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, dedupeId);
        }
    }

    public static class MyTaskParams {
        StatusBarNotification sbn;
        Context context;

        MyTaskParams(StatusBarNotification sbn, Context context) {
            this.sbn = sbn;
            this.context = context;
        }
    }

}
