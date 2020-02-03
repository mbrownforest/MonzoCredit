package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    Button monzoLogin;
    Button monzoAddPot;
    Button monzoViewPots;
    Permissions permissions = new Permissions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monzoLogin = (Button) findViewById(R.id.monzo_login);
        monzoAddPot = (Button) findViewById(R.id.monzo_pot);
        monzoViewPots = (Button) findViewById(R.id.monzo_pots);
        monzoLogin.setOnClickListener(this);
        monzoAddPot.setOnClickListener(this);
        monzoViewPots.setOnClickListener(this);
        permissions.checkForInternetPermission(this, this);
        startService(new Intent(this, NotificationListener.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monzo_login: {
                startActivity(new Intent(this, MonzoLoginActivity.class));
            }
            case R.id.monzo_pot: {
                startActivity(new Intent(this, MonzoPotManagerActivity.class));
            }
            case R.id.monzo_pots: {
                startActivity(new Intent(this, MonzoViewPotsActivity.class));
            }
        }
    }

}
