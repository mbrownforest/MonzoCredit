package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.tescotomonzo.AuthConfig.REDIRECT_URI;
import static com.example.tescotomonzo.GeneralConfig.REDIRECT_MONZO;

public class MonzoLoginActivity extends Activity {

    private Map<String, String> params = new LinkedHashMap<>();
    private String redirect_monzo = REDIRECT_MONZO;
    private Access access = new Access();

    private String clientId;
    private String clientSecret;

    private EditText editClientSecret;
    private EditText editClientId;

    private Button mainMenu;
    private Button login;
    private Button setupDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupDetails = findViewById(R.id.monzo_setup_details);

        login = findViewById(R.id.monzo_login_go);
        mainMenu = findViewById(R.id.return_main_menu);
        mainMenu.setOnClickListener(v -> finish());

        editClientId = findViewById(R.id.client_id);
        editClientSecret = findViewById(R.id.client_secret);

        AuthConfig authConfig = new AuthConfig();

        setupDetails.setOnClickListener(v -> startActivity(new Intent(this, SetupActivity.class)));

        login.setOnClickListener(v ->
        {
            if (!editClientId.getText().toString().isEmpty() && !editClientSecret.getText().toString().isEmpty()) {
                clientId = editClientId.getText().toString();
                clientSecret = editClientSecret.getText().toString();

                authConfig.setClientId(this, clientId);
                authConfig.setClientSecret(this, clientSecret);
                loginToMonzo();
            } else {
                Toast.makeText(this, "You need to input client id and secret", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loginToMonzo() {
        String state = getRandomState();
        access.setState(this, state);

        params.put("client_id", clientId);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("response_type", "code");
        params.put("state", state);
        try {
            redirect_monzo += ParameterStringBuilder.getParamsString(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(redirect_monzo));
        startActivity(viewIntent);
        finish();
    }

    private String getRandomState() {
        return RandomStringUtils.random(32, true, true);
    }

}
