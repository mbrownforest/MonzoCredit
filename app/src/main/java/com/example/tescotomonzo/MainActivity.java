package com.example.tescotomonzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button tescoLogin;
    Button monzoLogin;
    Permissions permissions = new Permissions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tescoLogin = (Button) findViewById(R.id.read_texts);
        monzoLogin = (Button) findViewById(R.id.monzo_login);
        tescoLogin.setOnClickListener(this);
        monzoLogin.setOnClickListener(this);
        permissions.checkForInternetPermission(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monzo_login:
                startActivity(new Intent(this, MonzoLoginActivity.class));
                break;
            case R.id.read_texts:
                permissions.checkForSmsPermission(this,this);
                permissions.checkForBroadcastPermission(this,this);
                break;
        }:w
    }

}
