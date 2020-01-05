package com.example.tescotomonzo;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {

    private MainActivity mainActivity;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        NotificationReader.MyTaskParams myTaskParams = new NotificationReader.MyTaskParams(sbn, this, mainActivity);
        new NotificationReader().execute(myTaskParams);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        sbn.getKey();
    }

}
