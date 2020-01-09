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

}
