package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class MonzoSavedNewPotActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_new_pot);

        TextView savedMonzoPot = findViewById(R.id.monzo_saved_pot_name);
        TextView savedCreditCardNotification = findViewById(R.id.monzo_saved_credit_card_notification);

        Button accept = findViewById(R.id.accept_pot);
        Button decline = findViewById(R.id.decline_pot);

        UserCreditValues userCreditValues = new UserCreditValues();

        Bundle potBundle = getIntent().getExtras();
        if (potBundle != null) {
            String creditCardInput = potBundle.getString("creditCardInput");
            String monzoPotName = potBundle.getString("monzoPotName");

            savedMonzoPot.setText("New Monzo Pot: " + monzoPotName);
            savedCreditCardNotification.setText("New Monzo Notification: \"" + creditCardInput + "\"");

            accept.setOnClickListener(v -> {
                userCreditValues.setMonzoPotName(this, monzoPotName);
                String moneyAmount = StringUtils.substringBetween(creditCardInput, "£", " ");
                String creditCardNotification = StringUtils.remove(creditCardInput, "£" + moneyAmount);
                userCreditValues.setCreditCardNotification(this, creditCardNotification);
                finish();
            });
            decline.setOnClickListener(v -> {
                userCreditValues.setCreditCardNotification(this, null);
                userCreditValues.setMonzoPotName(this, null);
                startActivity(new Intent(this, MonzoPotManagerActivity.class));
            });
        }
    }

}
