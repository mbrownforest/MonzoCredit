package com.example.tescotomonzo;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        NotificationReaderService.MyTaskParams myTaskParams = new NotificationReaderService.MyTaskParams(sbn, this);
        new NotificationReaderService(new UserCreditValues(), new NotificationBalance(), new MonzoAPI()).execute(myTaskParams);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        sbn.getKey();
    }

    @Override
    public StatusBarNotification[] getActiveNotifications() {
        return new StatusBarNotification[]{};
    }

}
