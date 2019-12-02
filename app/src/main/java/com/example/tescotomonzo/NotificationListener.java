package com.example.tescotomonzo;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        MonzoAPI monzoAPI = new MonzoAPI();
        super.onNotificationPosted(sbn);
        String tickerText = sbn.getNotification().tickerText.toString();
        CharSequence charSequence = "Android";
        if (tickerText.contains(charSequence)) {
            Toast.makeText(this, "Notification received and read", Toast.LENGTH_SHORT).show();
            monzoAPI.checkAccessToken(this);
            //You have {charge} on your American Express Card
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        sbn.getKey();
    }

}
