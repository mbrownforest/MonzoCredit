package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MonzoViewPotsActivity extends Activity {

    TextView monzoPot;
    Button deletePot;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pots);
        context = this;

        UserCreditValues userCreditValues = new UserCreditValues();
        monzoPot = (TextView) findViewById(R.id.monzo_view_pot);
        monzoPot.setText(userCreditValues.getMonzoPotName(context));

        deletePot = (Button) findViewById(R.id.delete_pot);
        deletePot.setOnClickListener(v -> {
            userCreditValues.setMonzoPotName(context, null);
            userCreditValues.setCreditCardNotification(context, null);
        });

    }
}
