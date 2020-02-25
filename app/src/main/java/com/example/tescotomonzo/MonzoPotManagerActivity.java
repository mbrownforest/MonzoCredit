package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

public class MonzoPotManagerActivity extends Activity {

    EditText monzoPotNameInput;
    EditText creditCardNotificationInput;
    Button saveButton;
    Button mainMenu;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pot_manager);

        context = this;
        monzoPotNameInput = findViewById(R.id.monzo_pot_name);
        creditCardNotificationInput = findViewById(R.id.monzo_pot_notif);

        saveButton = findViewById(R.id.save_new_pot);

        saveButton.setOnClickListener(v -> {
            String monzoPotName = monzoPotNameInput.getText().toString();
            String creditCardInput = creditCardNotificationInput.getText().toString();

            if (!StringUtils.isBlank(monzoPotName) && !StringUtils.isBlank(creditCardInput)) {
                Intent potIntent = new Intent(context, MonzoSavedNewPotActivity.class);
                Bundle potBundle = new Bundle();
                potBundle.putString("creditCardInput", creditCardInput);
                potBundle.putString("monzoPotName", monzoPotName);
                potIntent.putExtras(potBundle);
                startActivity(potIntent);
                finish();
            }
        });

        mainMenu = findViewById(R.id.return_main_menu);
        mainMenu.setOnClickListener(v -> finish());
        monzoPotNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        creditCardNotificationInput.setInputType(InputType.TYPE_CLASS_TEXT);
    }

}
