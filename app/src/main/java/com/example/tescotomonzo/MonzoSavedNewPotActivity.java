package com.example.tescotomonzo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MonzoSavedNewPotActivity extends Activity {

    TextView savedMonzoPot;
    TextView savedCreditCardNotification;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_new_pot);
        savedMonzoPot = (TextView) findViewById(R.id.monzo_saved_pot_name);
        savedCreditCardNotification = (TextView) findViewById(R.id.monzo_saved_credit_card_notification);

        UserCreditValues userCreditValues = new UserCreditValues();
        savedMonzoPot.setText(userCreditValues.getMonzoPotName(this));
        savedCreditCardNotification.setText(userCreditValues.getCreditCardNotification(this));
    }
}
