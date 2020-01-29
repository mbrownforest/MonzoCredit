package com.example.tescotomonzo;

import android.content.Context;
import android.os.AsyncTask;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class NotificationReaderService extends AsyncTask<NotificationReaderService.MyTaskParams, Context, String> {

    private MonzoAPI monzoAPI = new MonzoAPI();
    private NotificationBalance balances = new NotificationBalance();
    private Context notificationContext;

    //https://developer.android.com/reference/java/lang/ref/WeakReference.html

    protected String doInBackground(MyTaskParams... myTaskParams) {
        notificationContext = myTaskParams[0].context;
        String ticker = myTaskParams[0].sbn.getNotification().extras.getString("android.text");
        if (ticker != null) {
            return ticker;
        }
        return "empty";
    }

    protected void onPostExecute(String tickerText) {
        String dedupeId = RandomStringUtils.random(10, true, true);
        UserCreditValues userCreditValues = new UserCreditValues();
        userCreditValues.getCreditCardNotification(notificationContext);

        if (tickerText.contains("with Amex") && tickerText.contains("1009")) {
            Log.d("Read notification", tickerText);
            balances.setAmexCharge(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, 2, dedupeId);
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
