package com.example.tescotomonzo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MonzoViewPotsActivity extends Activity {

    private static final String NO_POTS = "You do not have any Monzo pots";
    TextView monzoPot;
    Button deletePot;
    Context context;
    Button mainMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pots);

        context = this;

        monzoPot = (TextView) findViewById(R.id.monzo_view_pot);
        deletePot = (Button) findViewById(R.id.delete_pot);
        mainMenu = (Button) findViewById(R.id.return_main_menu);

        mainMenu.setOnClickListener(v -> finish());

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.view_pots_layout);
        layout.removeView(deletePot);
        UserCreditValues userCreditValues = new UserCreditValues();
        String monzoPotName = userCreditValues.getMonzoPotName(context);
        if (monzoPotName != null) {
            monzoPot.setText(userCreditValues.getMonzoPotName(context));
            layout.addView(deletePot);
            deletePot.setOnClickListener(v -> {
                userCreditValues.setMonzoPotName(context, null);
                userCreditValues.setCreditCardNotification(context, null);
                layout.removeView(deletePot);
                monzoPot.setText(NO_POTS);
            });
        } else {
            monzoPot.setText(NO_POTS);
        }

    }

}
