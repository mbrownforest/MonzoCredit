package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;
import org.apache.commons.lang3.StringUtils;

public class NotificationReader extends AsyncTask<NotificationReader.MyTaskParams, Context, String> {

    MonzoAPI monzoAPI = new MonzoAPI();
    NotificationBalance balances = new NotificationBalance();
    private Context notificationContext;
    private Activity parent;

    //https://developer.android.com/reference/java/lang/ref/WeakReference.html

    protected String doInBackground(MyTaskParams... myTaskParams) {
        notificationContext = myTaskParams[0].context;
        parent = myTaskParams[0].activity;
        String ticker = myTaskParams[0].sbn.getNotification().extras.getString("android.text");
        if (ticker != null) {
            return ticker;
        }
        return "empty String";
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String tickerText) {
        //notification updating the WEEKLY AMEX BALANCE
        if (tickerText.contains("The balance on your Amex Card ending 71009 is ")) {
            run("updating weekly Amex balance");
            balances.setAmexBalance(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, 1);
        }

        //notification updating a SINGLE AMEX CHARGE
        if (tickerText.contains("with Amex") && tickerText.contains("1009")) {
            Log.d("Read notification", tickerText);
            run("checking new Amex charge");
            balances.setAmexCharge(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, 2);
        }

        //notification updating a SINGLE TESCO CHARGE
        if (tickerText.contains("You'll never miss a Clubcard point again.")) {
            run("checking new Tesco charge");
            balances.setTescoCharge(notificationContext, StringUtils.substringBetween(tickerText, "£", " "));
            monzoAPI.checkAccessToken(notificationContext, 3);
        }
    }

    static class MyTaskParams {
        StatusBarNotification sbn;
        Context context;
        Activity activity;

        MyTaskParams(StatusBarNotification sbn, Context context, Activity activity) {
            this.sbn = sbn;
            this.context = context;
            this.activity = activity;
        }
    }

    public void run(String toastMessage) {
        parent.runOnUiThread(() -> Toast.makeText(parent.getBaseContext(), toastMessage, Toast.LENGTH_LONG).show());
    }
}
