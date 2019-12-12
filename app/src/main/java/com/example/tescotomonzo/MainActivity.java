package com.example.tescotomonzo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        startService(new Intent(this, NotificationListener.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monzo_login:
                startActivity(new Intent(this, MonzoLoginActivity.class));
                break;
            case R.id.read_texts:
                permissions.checkForSmsPermission(this, this);
                permissions.checkForBroadcastPermission(this, this);
                break;
        }
    }

}
