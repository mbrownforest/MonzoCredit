package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MonzoSavedNewPotActivity extends Activity implements View.OnClickListener {

    TextView savedMonzoPot;
    TextView savedCreditCardNotification;

    Button accept;
    Button decline;

    UserCreditValues userCreditValues = new UserCreditValues();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_new_pot);
        savedMonzoPot = (TextView) findViewById(R.id.monzo_saved_pot_name);
        savedCreditCardNotification = (TextView) findViewById(R.id.monzo_saved_credit_card_notification);

        accept = (Button) findViewById(R.id.accept_pot);
        decline = (Button) findViewById(R.id.decline_pot);

        accept.setOnClickListener(this);
        decline.setOnClickListener(this);

        savedMonzoPot.setText("New Monzo Pot: " + userCreditValues.getMonzoPotName(this));
        savedCreditCardNotification.setText("New Monzo Notification: \"" + userCreditValues.getCreditCardNotification(this) + "\"");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.accept_pot):
                finish();
                break;
            case (R.id.decline_pot):
                userCreditValues.setCreditCardNotification(this, null);
                userCreditValues.setMonzoPotName(this, null);
                startActivity(new Intent(this, MonzoPotManagerActivity.class));
                break;
        }

    }
}
