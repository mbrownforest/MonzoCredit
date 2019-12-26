package com.example.tescotomonzo;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

public class NotificationListener extends NotificationListenerService {

    String amexWeekly = "The balance on your Amex Card ending 71009 is £631.99 as of 10/12/2019";
    String amexString = "The balance on your Amex Card ending 71009 is ";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        MonzoAPI monzoAPI = new MonzoAPI();
        super.onNotificationPosted(sbn);
        String tickerText = sbn.getNotification().tickerText.toString();

        //change to ticketText but for now use amexWeekly
        String amexCheck = StringUtils.substring(amexWeekly,0,46);
        if (amexCheck.equals(amexString)) {
            Toast.makeText(this, "AMEX balance updating", Toast.LENGTH_LONG).show();
            Float amexBalance = Float.valueOf(StringUtils.substringBetween(amexWeekly, "£"," "));
            AmexBalance balances = new AmexBalance();
            balances.setAmexBalance(this, amexBalance);
            monzoAPI.checkAccessToken(this);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        sbn.getKey();
    }

}
