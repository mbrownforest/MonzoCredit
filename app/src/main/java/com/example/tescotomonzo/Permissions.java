package com.example.tescotomonzo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    private static final int MY_PERMISSIONS_INTERNET = 11235;
    private static final int MY_PERMISSIONS_SMS = 12;
    private static final int MY_PERMISSIONS_BROADCAST = 3;

    public void checkForInternetPermission(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("Nein permission", Manifest.permission.INTERNET);
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_INTERNET);
            }

        }

    }

    public void checkForSmsPermission(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("Nay permission", Manifest.permission.READ_SMS);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_SMS);
        }
    }

    public void checkForBroadcastPermission(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BROADCAST_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("No permission", Manifest.permission.BROADCAST_SMS);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BROADCAST_SMS},
                    MY_PERMISSIONS_BROADCAST);
        }
    }

}
