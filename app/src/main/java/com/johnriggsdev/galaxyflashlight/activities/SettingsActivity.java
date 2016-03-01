package com.johnriggsdev.galaxyflashlight.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.johnriggsdev.galaxyflashlight.R;
import com.johnriggsdev.galaxyflashlight.baseLevelParts.BaseActivity;
import com.johnriggsdev.galaxyflashlight.models.User;
import com.johnriggsdev.galaxyflashlight.utils.ColorThemes;
import com.johnriggsdev.galaxyflashlight.utils.ImagesHelper;
import com.squareup.picasso.Picasso;

import static com.johnriggsdev.galaxyflashlight.utils.Constants.*;

public class SettingsActivity extends BaseActivity {

    RadioButton flashLedScreen;
    RadioButton flashLed;
    RadioButton flashScreen;
    RadioButton flashScreenNoLed;
    RadioButton backgroundFlashYes;
    RadioButton backgroundFlashNo;
    RadioButton headingAzimuth;
    RadioButton headingQuadrant;
    RadioButton wakeLockYes;
    RadioButton wakeLockNo;

    View purple;
    View blue;
    View green;
    View orange;
    View red;
    TextView purpleCheck;
    TextView blueCheck;
    TextView greenCheck;
    TextView orangeCheck;
    TextView redCheck;

    ImageView compassRoseBlock;
    ImageView compassRoseScript;
    TextView compassRoseBlockCheck;
    TextView compassRoseScriptCheck;

    TextView calibrationMessage;
    TextView buildMassage;
    TextView appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        associateMembersToLayoutViews();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();

        Picasso.with(this).load(R.drawable.compass_rose_block_white).into(compassRoseBlock);
        Picasso.with(this).load(R.drawable.compass_rose_script_white).into(compassRoseScript);

        setWakeLock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void associateMembersToLayoutViews() {
        flashLedScreen = (RadioButton) findViewById(R.id.led_and_screen);
        flashLed = (RadioButton) findViewById(R.id.led_only);
        flashScreen = (RadioButton) findViewById(R.id.screen_only);
        flashScreenNoLed = (RadioButton) findViewById(R.id.screen_only_no_led);
        backgroundFlashYes = (RadioButton) findViewById(R.id.background_yes);
        backgroundFlashNo = (RadioButton) findViewById(R.id.background_no);
        headingAzimuth = (RadioButton) findViewById(R.id.azimuth);
        headingQuadrant = (RadioButton) findViewById(R.id.quadrant);
        wakeLockYes = (RadioButton) findViewById(R.id.wake_lock_yes);
        wakeLockNo = (RadioButton) findViewById(R.id.wake_lock_no);
        purple = findViewById(R.id.purple);
        blue = findViewById(R.id.blue);
        green = findViewById(R.id.green);
        orange = findViewById(R.id.orange);
        red = findViewById(R.id.red);
        purpleCheck = (TextView) findViewById(R.id.purple_check);
        blueCheck = (TextView) findViewById(R.id.blue_check);
        greenCheck = (TextView) findViewById(R.id.green_check);
        orangeCheck = (TextView) findViewById(R.id.orange_check);
        redCheck = (TextView) findViewById(R.id.red_check);
        compassRoseBlock = (ImageView) findViewById(R.id.compass_rose_block);
        compassRoseScript = (ImageView) findViewById(R.id.compass_rose_script);
        compassRoseBlockCheck = (TextView) findViewById(R.id.compass_rose_block_check);
        compassRoseScriptCheck = (TextView) findViewById(R.id.compass_rose_script_check);
        calibrationMessage = (TextView) findViewById(R.id.calibration_message);
        buildMassage = (TextView) findViewById(R.id.build_message);
        appVersion = (TextView) findViewById(R.id.app_version);
    }

    private void setupListeners() {
        flashLedScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flashModeTouched(flashLedScreen);
                return false;
            }
        });
        flashLed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flashModeTouched(flashLed);
                return false;
            }
        });
        flashScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flashModeTouched(flashScreen);
                return false;
            }
        });
        backgroundFlashYes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                backgroundTouched(backgroundFlashYes);
                return false;
            }
        });
        backgroundFlashNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                backgroundTouched(backgroundFlashNo);
                return false;
            }
        });
        headingAzimuth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                headingTouched(headingAzimuth);
                return false;
            }
        });
        headingQuadrant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                headingTouched(headingQuadrant);
                return false;
            }
        });
        wakeLockYes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wakeLockTouched(wakeLockYes);
                return false;
            }
        });
        wakeLockNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wakeLockTouched(wakeLockNo);
                return false;
            }
        });
        purple.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                colorTouched(purple);
                return false;
            }
        });
        blue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                colorTouched(blue);
                return false;
            }
        });
        green.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                colorTouched(green);
                return false;
            }
        });
        orange.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                colorTouched(orange);
                return false;
            }
        });
        red.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                colorTouched(red);
                return false;
            }
        });
        compassRoseBlock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                compassRoseTouched(compassRoseBlock);
                return false;
            }
        });
        compassRoseScript.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                compassRoseTouched(compassRoseScript);
                return false;
            }
        });
        calibrationMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String url = GOOGLE_CALIBRATION;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return false;
            }
        });
        buildMassage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String url = JRDCOM_TUTORIAL;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return false;
            }
        });
    }

    private void setWakeLock(){
        if (User.getInstance().getWakeLock().equals(WAKE_LOCK_ON)){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void flashModeTouched(RadioButton button) {
        if (button == flashLedScreen) {
            User.getInstance().setFlashMode(FLASH_LED_AND_SCREEN);
        } else if (button == flashLed) {
            User.getInstance().setFlashMode(FLASH_LED_ONLY);
        } else {
            User.getInstance().setFlashMode(FLASH_SCREEN_ONLY);
        }
    }

    private void backgroundTouched(RadioButton button) {
        if (button == backgroundFlashYes) {
            User.getInstance().setFlashInBackground(FLASH_ON_IN_BACKGROUND);
        } else {
            User.getInstance().setFlashInBackground(FLASH_OFF_IN_BACKGROUND);
        }
    }

    private void headingTouched(RadioButton button) {
        if (button == headingAzimuth) {
            User.getInstance().setHeadingDisplay(HEADING_AZIMUTH);
        } else {
            User.getInstance().setHeadingDisplay(HEADING_QUADRANT);
        }
    }

    private void wakeLockTouched(RadioButton button) {
        if (button == wakeLockYes){
            User.getInstance().setWakeLock(WAKE_LOCK_ON);
        } else {
            User.getInstance().setWakeLock(WAKE_LOCK_OFF);
        }

        setWakeLock();
    }

    private void colorTouched(View view){
        if (view == purple){
            User.getInstance().setTheme(THEME_PURPLE);
        } else if (view == blue) {
            User.getInstance().setTheme(THEME_BLUE);
        } else if (view == green) {
            User.getInstance().setTheme(THEME_GREEN);
        } else if (view == orange) {
            User.getInstance().setTheme(THEME_ORANGE);
        } else {
            User.getInstance().setTheme(THEME_RED);
        }

        loadColorScheme();
    }

    private void compassRoseTouched(ImageView image){
        if (image == compassRoseBlock){
            User.getInstance().setCompassRose(COMPASS_ROSE_BLOCK);
        } else {
            User.getInstance().setCompassRose(COMPASS_ROSE_SCRIPT);
        }

        loadCompassRose(User.getInstance().getTheme());
    }

    private void loadSettings(){
        loadFlashlightMode();
        loadBackgroundMode();
        loadWakeLock();
        loadColorScheme();
        loadHeadingDisplay();
        loadCompassRose(User.getInstance().getTheme());
        loadAppVersion();
    }

    private void loadFlashlightMode(){
        if (User.getInstance().getFlashMode().equals(FLASH_SCREEN_ONLY_NO_LED)){
            flashScreenNoLed.setVisibility(View.VISIBLE);
            flashLedScreen.setVisibility(View.GONE);
            flashLed.setVisibility(View.GONE);
            flashScreen.setVisibility(View.GONE);
            flashScreenNoLed.setChecked(true);
        } else {
            if (User.getInstance().getFlashMode().equals(FLASH_LED_AND_SCREEN)){
                flashLedScreen.setChecked(true);
            } else if (User.getInstance().getFlashMode().equals(FLASH_LED_ONLY)){
                flashLed.setChecked(true);
            } else {
                flashScreen.setChecked(true);
            }
        }
    }

    private void loadBackgroundMode(){
        if (User.getInstance().getFlashInBackground().equals(FLASH_ON_IN_BACKGROUND)){
            backgroundFlashYes.setChecked(true);
        } else {
            backgroundFlashNo.setChecked(true);
        }
    }

    private void loadWakeLock(){
        if (User.getInstance().getWakeLock().equals(WAKE_LOCK_ON)){
            wakeLockYes.setChecked(true);
        } else {
            wakeLockNo.setChecked(true);
        }
    }

    private void loadColorScheme(){
        String theme = User.getInstance().getTheme();

        //first clear any color checkmarks
        purpleCheck.setVisibility(View.GONE);
        blueCheck.setVisibility(View.GONE);
        greenCheck.setVisibility(View.GONE);
        orangeCheck.setVisibility(View.GONE);
        redCheck.setVisibility(View.GONE);

        //check the selected color and change the compassRose tint
        if (theme.equals(THEME_PURPLE)){
            purpleCheck.setVisibility(View.VISIBLE);
        } else if (theme.equals(THEME_BLUE)){
            blueCheck.setVisibility(View.VISIBLE);
        } else if (theme.equals(THEME_GREEN)){
            greenCheck.setVisibility(View.VISIBLE);
        } else if (theme.equals(THEME_ORANGE)){
            orangeCheck.setVisibility(View.VISIBLE);
        } else if (theme.equals(THEME_RED)){
            redCheck.setVisibility(View.VISIBLE);
        }

        loadCompassRose(theme);
    }

    private void loadHeadingDisplay(){
        if (User.getInstance().getHeadingDisplay().equals(HEADING_AZIMUTH)){
            headingAzimuth.setChecked(true);
        } else {
            headingQuadrant.setChecked(true);
        }
    }

    private void loadCompassRose(String theme){
        //Clear the checkmarks first
        compassRoseBlockCheck.setVisibility(View.GONE);
        compassRoseScriptCheck.setVisibility(View.GONE);

        if (User.getInstance().getCompassRose().equals(COMPASS_ROSE_BLOCK)) {
            compassRoseBlockCheck.setVisibility(View.VISIBLE);
        } else {
            compassRoseScriptCheck.setVisibility(View.VISIBLE);
        }

        ColorThemes.setImageView(compassRoseBlock, theme);
        ColorThemes.setImageView(compassRoseScript, theme);
    }

    private void loadAppVersion(){
        String version = "";
        String buildNumber = "";

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            buildNumber = String.valueOf(pInfo.versionCode);
        } catch (Exception e) {
            version = "-1";
            buildNumber = "-1";
            Log.e(SettingsActivity.class.getCanonicalName(), "Failed to retrieve version number");
        }
        appVersion.setText(getResources().getString(R.string.version) + " " + version + " (" + buildNumber
                + ") " + getResources().getString(R.string.copyright_symbol) + "2016 " + getResources().getString(R.string.jrdcom));
    }
}
