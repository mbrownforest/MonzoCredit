package com.example.tescotomonzo;

import android.content.Context;
import android.os.AsyncTask;
import android.service.notification.StatusBarNotification;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class NotificationReaderService extends AsyncTask<NotificationReaderService.MyTaskParams, Context, String> {

    private static final String WRONG_NOTIFICATION = "Not credit or empty notification";
    private MonzoAPI monzoAPI = new MonzoAPI();
    private NotificationBalance balances = new NotificationBalance();
    private Context notificationContext;

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
        UserCreditValues userCreditValues = new UserCreditValues();
        String creditCardNotification = userCreditValues.getCreditCardNotification(notificationContext);

        if (tickerText.contains(creditCardNotification)) {
            balances.setAmexCharge(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, dedupeId);
        }
    }

    static class MyTaskParams {
        StatusBarNotification sbn;
        Context context;

        MyTaskParams(StatusBarNotification sbn, Context context) {
            this.sbn = sbn;
            this.context = context;
        }
    }

}
