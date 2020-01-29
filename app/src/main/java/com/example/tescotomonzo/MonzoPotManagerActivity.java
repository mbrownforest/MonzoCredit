package com.example.tescotomonzo;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

public class MonzoPotManagerActivity extends Activity {

    EditText monzoPotNameInput;
    EditText creditCardNotificationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pot_manager);
        monzoPotNameInput = (EditText) findViewById(R.id.monzo_pot_name);
        creditCardNotificationInput = (EditText) findViewById(R.id.monzo_pot_notif);
        monzoPotNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        creditCardNotificationInput.setInputType(InputType.TYPE_CLASS_TEXT);
        String monzoPotName = monzoPotNameInput.getText().toString();
        String creditCardNotification = creditCardNotificationInput.getText().toString();
        UserCreditValues userCreditValues = new UserCreditValues();
        userCreditValues.setMonzoPotName(this, monzoPotName);
        userCreditValues.setCreditCardNotification(this, creditCardNotification);
    }
}
