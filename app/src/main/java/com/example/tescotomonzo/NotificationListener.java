package com.example.tescotomonzo;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        sbn.getKey();
        Bundle bundle = sbn.getNotification().extras;
        String temp = "";
        CharSequence[] lines = bundle.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
        for(CharSequence line : lines){
            temp += "line: " + line.toString() + "\n";
        }
        Toast.makeText(this,"Notification Received", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        sbn.getKey();
    }

}
