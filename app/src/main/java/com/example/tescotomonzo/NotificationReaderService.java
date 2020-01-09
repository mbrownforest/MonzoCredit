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
//        String tText = myTaskParams[0].sbn.getNotification().tickerText.toString();
        //TODO add in tText checks for Google Pay and Tesco Pay
        if (ticker != null) {
            return ticker;
        }
        return "empty";
    }

    protected void onPostExecute(String tickerText) {
        String dedupeId = RandomStringUtils.random(10, true, true);
        //notification updating the WEEKLY AMEX BALANCE
        if (tickerText.contains("The balance on your Amex Card ending 71009 is ")) {
            balances.setAmexBalance(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, 1, dedupeId);
        }

        //notification updating a SINGLE AMEX CHARGE
        if (tickerText.contains("with Amex") && tickerText.contains("1009")) {
            Log.d("Read notification", tickerText);
            balances.setAmexCharge(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, 2, dedupeId);
        }

        //notification updating a SINGLE TESCO CHARGE
        if (tickerText.contains("You'll never miss a Clubcard point again.")) {
            balances.setTescoCharge(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, 3, dedupeId);
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
