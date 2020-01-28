package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    Button monzoLogin;
    Permissions permissions = new Permissions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monzoLogin = (Button) findViewById(R.id.monzo_login);
        monzoLogin.setOnClickListener(this);
        permissions.checkForInternetPermission(this, this);
        //     this.registerReceiver(new UserPresentReceiver(), new IntentFilter(ACTION_USER_PRESENT));
        startService(new Intent(this, NotificationListener.class));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.monzo_login) {
            startActivity(new Intent(this, MonzoLoginActivity.class));
        }
    }

}
