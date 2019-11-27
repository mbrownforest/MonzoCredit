package com.example.tescotomonzo;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        MoveMoney moveMoney = new MoveMoney();
        super.onNotificationPosted(sbn);
        String tickerText = sbn.getNotification().tickerText.toString();
        CharSequence charSequence = "Android";
        if (tickerText.contains(charSequence)) {
            Toast.makeText(this, "Notification received and read", Toast.LENGTH_SHORT).show();
            moveMoney.makeMoneyMoves(this);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        sbn.getKey();
    }

}
