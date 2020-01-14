package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.tescotomonzo.AuthConfig.REDIRECT_URI;

public class MainActivity extends Activity implements View.OnClickListener {

    private Permissions permissions = new Permissions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Map<String, String> params = new HashMap<>();
        params.put("redirect_uri", REDIRECT_URI);
        JSONObject jsonObject = new JSONObject(params);
        setContentView(R.layout.activity_main);
        Button monzoLogin = (Button) findViewById(R.id.monzo_login);
        monzoLogin.setOnClickListener(this);
        permissions.checkForInternetPermission(this, this);
        RequestManager.getInstance(this);
        startService(new Intent(this, NotificationListener.class));
    }

    @Override
    public void onClick(View v) {
            startActivity(new Intent(this, MonzoLoginActivity.class));
    }

}
