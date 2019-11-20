package com.example.tescotomonzo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_INTERNET = 11235;
    Button tescoLogin;
    Button monzoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tescoLogin = (Button) findViewById(R.id.tesco_login);
        monzoLogin = (Button) findViewById(R.id.monzo_login);
        tescoLogin.setOnClickListener(this);
        monzoLogin.setOnClickListener(this);
        checkForInternetPermission();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monzo_login:
                startActivity(new Intent(this, MonzoLoginActivity.class));
                break;
            case R.id.tesco_login:
                break;
        }
    }

    private void checkForInternetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("YE PERMISSION BE NOT ", "GRANTED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_INTERNET);
            }

        }

    }
}
