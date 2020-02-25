package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    Button monzoLogin;
    Button monzoAddPot;
    Button monzoViewPots;
    Button close;
    Permissions permissions = new Permissions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monzoLogin = findViewById(R.id.monzo_login);
        monzoAddPot = findViewById(R.id.monzo_pot);
        monzoViewPots = findViewById(R.id.monzo_pots);
        close = findViewById(R.id.close);

        monzoLogin.setOnClickListener(v -> startActivity(new Intent(this, MonzoLoginActivity.class)));
        monzoAddPot.setOnClickListener(v -> startActivity(new Intent(this, MonzoPotManagerActivity.class)));
        monzoViewPots.setOnClickListener(v -> startActivity(new Intent(this, MonzoViewPotsActivity.class)));
        close.setOnClickListener(v -> finish());
        permissions.checkForInternetPermission(this, this);
        startService(new Intent(this, NotificationListener.class));
    }

}
