package com.example.tescotomonzo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class SetupActivity extends Activity {

    Button returnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        returnLogin = findViewById(R.id.return_login);
        returnLogin.setOnClickListener(v -> finish());
    }
}
