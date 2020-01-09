package com.example.tescotomonzo;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;

import java.util.Objects;

import androidx.annotation.RequiresApi;

public class UserPresentReceiver extends BroadcastReceiver {

    //Might not be needed

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_USER_PRESENT)) {
           NotificationListener notificationListener = new NotificationListener();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationListener.getSnoozedNotifications();
            }
        }
    }

//    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
//            if (notificationManager != null) {
//        StatusBarNotification[] sbns = notificationManager.getActiveNotifications();
//        for (int i = 0; i < sbns.length; i++) {
//            NotificationReaderService.MyTaskParams myTaskParams = new NotificationReaderService.MyTaskParams(sbns[i], context);
//            new NotificationReaderService().execute(myTaskParams);
//        }
//    }
}
