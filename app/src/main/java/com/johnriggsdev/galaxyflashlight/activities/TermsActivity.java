package com.johnriggsdev.galaxyflashlight.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.johnriggsdev.galaxyflashlight.R;
import com.johnriggsdev.galaxyflashlight.baseLevelParts.BaseActivity;
import com.johnriggsdev.galaxyflashlight.events.TermsAcceptedEvent;
import com.johnriggsdev.galaxyflashlight.events.TermsDeclinedEvent;
import com.johnriggsdev.galaxyflashlight.utils.BusDriver;
import com.johnriggsdev.galaxyflashlight.utils.DialogHelper;
import com.squareup.otto.Subscribe;

public class TermsActivity extends BaseActivity{
    Button termsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        BusDriver.getBus().register(this);

        termsButton = (Button) findViewById(R.id.terms_button);
        termsButton.setOnClickListener(new ButtonClickedListener());
    }

    class ButtonClickedListener implements CompoundButton.OnClickListener {

        @Override
        public void onClick(View v) {
            promptForTerms();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BusDriver.getBus().unregister(this);
    }

    private void promptForTerms() {
        DialogHelper.showDialog(this,
                getString(R.string.accept_decline_terms),
                getString(R.string.accept_decline_terms_message),
                getString(R.string.accept), positiveListener,
                getString(R.string.decline), negativeListener);
    }

    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            BusDriver.getBus().post(new TermsAcceptedEvent());
        }
    };

    DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            BusDriver.getBus().post(new TermsDeclinedEvent());
        }
    };

    @Subscribe public void termsAccepted(TermsAcceptedEvent event){
        finish();
    }

    @Subscribe public void termsDeclined(TermsDeclinedEvent event){
        finish();
    }
}
