package com.example.tescotomonzo;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        MonzoAPI monzoAPI = new MonzoAPI();
        NotificationBalance balances = new NotificationBalance();
        super.onNotificationPosted(sbn);
        String tickerText = sbn.getNotification().tickerText.toString();

        //notification updating the WEEKLY AMEX BALANCE
        if (tickerText.contains("The balance on your Amex Card ending 71009 is ")) {
            Toast.makeText(this, "updating weekly Amex balance", Toast.LENGTH_LONG).show();
            balances.setAmexBalance(this, StringUtils.substringBetween(tickerText, "£"," "));
            monzoAPI.checkAccessToken(this, 1);
        }

        //notification updating a SINGLE AMEX CHARGE
        if (tickerText.contains("with Amex") && tickerText.contains("1009")){
            Toast.makeText(this, "checking new Amex charge", Toast.LENGTH_LONG).show();
            balances.setAmexCharge(this, StringUtils.substringBetween(tickerText,"£", " "));
            monzoAPI.checkAccessToken(this, 2);
        }

        //notification updating a SINGLE TESCO CHARGE
        if (tickerText.contains("You'll never miss a Clubcard point again.")){
            Toast.makeText(this, "checking new Tesco charge", Toast.LENGTH_LONG).show();
            balances.setTescoCharge(this, StringUtils.substringBetween(tickerText,"£", " "));
            monzoAPI.checkAccessToken(this, 3);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        sbn.getKey();
    }

}
