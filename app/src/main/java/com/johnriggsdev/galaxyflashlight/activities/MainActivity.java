package com.johnriggsdev.galaxyflashlight.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.johnriggsdev.galaxyflashlight.R;
import com.johnriggsdev.galaxyflashlight.baseLevelParts.BaseActivity;
import com.johnriggsdev.galaxyflashlight.events.ScreenFlashEvent;
import com.johnriggsdev.galaxyflashlight.events.TermsAcceptedEvent;
import com.johnriggsdev.galaxyflashlight.events.TermsDeclinedEvent;
import com.johnriggsdev.galaxyflashlight.fragments.FlashlightFragment;
import com.johnriggsdev.galaxyflashlight.fragments.CompassFragment;
import com.johnriggsdev.galaxyflashlight.models.User;
import com.johnriggsdev.galaxyflashlight.services.Camera2LedService;
import com.johnriggsdev.galaxyflashlight.services.CameraLedService;
import com.johnriggsdev.galaxyflashlight.utils.BusDriver;
import com.johnriggsdev.galaxyflashlight.utils.ColorThemes;
import static com.johnriggsdev.galaxyflashlight.utils.Constants.*;

import com.squareup.otto.Subscribe;

public class MainActivity extends BaseActivity {

    CompassFragment compassFragment;
    FlashlightFragment flashlightFragment;

    ImageView settingsIcon;
    RelativeLayout mainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Register this class to listen for BusDriver events
        BusDriver.getBus().register(this);

        mainScreen = (RelativeLayout) findViewById(R.id.activity_main_background);
        settingsIcon = (ImageView) findViewById(R.id.settings_icon);

        settingsIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                launchSettings();
                return false;
            }
        });

        checkForTerms();
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        flashlightFragment = new FlashlightFragment();
        transaction.add(R.id.flashlight_fragment, flashlightFragment);
        compassFragment = new CompassFragment();
        transaction.add(R.id.compass_fragment, compassFragment);
        transaction.commit();

        setWakeLock();

        enforceCorrectScreenState(mainScreen);
        ColorThemes.setImageView(settingsIcon, User.getInstance().getTheme());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Kill the LED flash if it's on and the MainActivity gets closed
        if (User.getInstance().getFlashOn() && User.getInstance().getAndroidVersion().equals(LOLLIPOP)){
            Intent intent = new Intent(MainActivity.this, Camera2LedService.class);
            stopService(intent);
        } else if (User.getInstance().getFlashOn()){
            Intent intent = new Intent(MainActivity.this, CameraLedService.class);
            stopService(intent);
        }

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Unregister the class with the BusDriver to prevent fatal crashes
        BusDriver.getBus().unregister(this);
    }

    private void checkForTerms(){
        if (!User.getInstance().getAcceptedTerms()){
            launchTerms();
        }
    }

    private void setWakeLock(){
        if (User.getInstance().getWakeLock().equals(WAKE_LOCK_ON)){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void launchTerms(){
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void launchSettings(){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void enforceCorrectScreenState(View view){
        // Compare the background color of view to a predefined color in colors.xml
        // Only works if background is a solid color
        ColorDrawable colorDrawable = new ColorDrawable();
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            colorDrawable = (ColorDrawable) drawable;
        }

        if (User.getInstance().getFlashMode().equals(FLASH_LED_ONLY)){
            if ((colorDrawable != null) && (colorDrawable.getColor() == getResources().getColor(R.color.white))){
                BusDriver.getBus().post(new ScreenFlashEvent(SCREEN_FLASH_OFF));
            }
        } else if (!User.getInstance().getFlashOn()){
            if ((colorDrawable != null) && (colorDrawable.getColor() == getResources().getColor(R.color.white))){
                BusDriver.getBus().post(new ScreenFlashEvent(SCREEN_FLASH_OFF));
            }
        } else if (User.getInstance().getFlashOn()){
            if ((colorDrawable != null) && (colorDrawable.getColor() == getResources().getColor(R.color.grey_dark))){
                BusDriver.getBus().post(new ScreenFlashEvent(SCREEN_FLASH_ON));
            }
        }
    }

    @Subscribe public void setScreenFlash(ScreenFlashEvent event){
        if (event.screenFlashState.equals(SCREEN_FLASH_ON)){
            mainScreen.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (event.screenFlashState.equals(SCREEN_FLASH_OFF)){
            mainScreen.setBackgroundColor(getResources().getColor(R.color.grey_dark));
        }
    }

    @Subscribe public void termsAccepted(TermsAcceptedEvent event){
        User.getInstance().setAcceptedTerms(true);
    }

    @Subscribe public void termsDeclined(TermsDeclinedEvent event){
        finish();
    }
}
