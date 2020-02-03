package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

public class MonzoPotManagerActivity extends Activity implements View.OnClickListener {

    EditText monzoPotNameInput;
    EditText creditCardNotificationInput;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pot_manager);
        monzoPotNameInput = (EditText) findViewById(R.id.monzo_pot_name);
        creditCardNotificationInput = (EditText) findViewById(R.id.monzo_pot_notif);
        saveButton = (Button) findViewById(R.id.save_new_pot);
        saveButton.setOnClickListener(this);
        monzoPotNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        creditCardNotificationInput.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    @Override
    public void onClick(View v) {
        UserCreditValues userCreditValues = new UserCreditValues();

        String monzoPotName = monzoPotNameInput.getText().toString();
        userCreditValues.setMonzoPotName(this, monzoPotName);

        String creditCardInput = creditCardNotificationInput.getText().toString();
        String moneyAmount = StringUtils.substringBetween(creditCardInput, "£", " ");
        String creditCardNotification = StringUtils.remove(creditCardInput, "£" + moneyAmount);
        userCreditValues.setCreditCardNotification(this, creditCardNotification);
        startActivity(new Intent(this, MonzoSavedNewPotActivity.class));
    }
}
