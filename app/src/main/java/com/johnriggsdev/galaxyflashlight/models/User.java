package com.johnriggsdev.galaxyflashlight.models;

import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import static com.johnriggsdev.galaxyflashlight.utils.Constants.*;
import com.johnriggsdev.galaxyflashlight.baseLevelParts.FlashApp;
import com.johnriggsdev.galaxyflashlight.utils.SharedPreferencesHelper;

public class User {
    private static User user = new User();

    private String androidVersion = "";
    private String compassRose = "";
    private String flashMode = "";
    private String flashInBackground = "";
    private String headingDisplay = "";
    private String theme = "";
    private String wakeLock = "";
    private boolean acceptedTerms;
    private boolean isFlashOn;
    private boolean ledOn;

    public static User getInstance() {
        return user;
    }

    private User() {
        getUserFromSharedPreferences();
        if (androidVersion.isEmpty() || androidVersion.equals("")){
            setAndroidVersion();
        }
    }

    // Retrieve the User's saved settings or apply predefined defaults if no saved data present
    private void  getUserFromSharedPreferences() {
        acceptedTerms = SharedPreferencesHelper.getBoolean(USER_STORAGE_FILE_NAME, USER_ACCEPTED_TERMS);

        compassRose = SharedPreferencesHelper.getString(USER_STORAGE_FILE_NAME, USER_COMPASS_ROSE);
        if (compassRose.isEmpty()){
            compassRose = COMPASS_ROSE_BLOCK;
        }

        flashMode = SharedPreferencesHelper.getString(USER_STORAGE_FILE_NAME, USER_FLASH_MODE);
        if (flashMode.isEmpty()){
            if (!checkForFlash()){
                flashMode = FLASH_SCREEN_ONLY_NO_LED;
            } else {
                flashMode = FLASH_LED_AND_SCREEN;
            }
        }

        flashInBackground = SharedPreferencesHelper.getString(USER_STORAGE_FILE_NAME, USER_FLASH_IN_BACKGROUND);
        if (flashInBackground.isEmpty()){
            flashInBackground = FLASH_ON_IN_BACKGROUND;
        }

        headingDisplay = SharedPreferencesHelper.getString(USER_STORAGE_FILE_NAME, USER_HEADING);
        if (headingDisplay.isEmpty()){
            headingDisplay = HEADING_AZIMUTH;
        }

        theme = SharedPreferencesHelper.getString(USER_STORAGE_FILE_NAME, USER_THEME);
        if (theme.isEmpty()){
            theme = THEME_PURPLE;
        }

        wakeLock = SharedPreferencesHelper.getString(USER_STORAGE_FILE_NAME, USER_WAKE_LOCK);
        if (wakeLock.isEmpty()){
            wakeLock = WAKE_LOCK_OFF;
        }
    }

    public void setAcceptedTerms(boolean acceptedTerms){
        this.acceptedTerms = acceptedTerms;
        SharedPreferencesHelper.addBoolean(USER_STORAGE_FILE_NAME, USER_ACCEPTED_TERMS, acceptedTerms);
    }

    public boolean getAcceptedTerms(){
        return acceptedTerms;
    }

    private void setAndroidVersion(){
        if (Build.VERSION.SDK_INT <= 20){
            this.androidVersion = KITKAT;
        } else if (Build.VERSION.SDK_INT >= 23) {
            this.androidVersion = MARSHMALLOW;
        } else {
            this.androidVersion = LOLLIPOP;
        }
    }

    public String getAndroidVersion(){
        return androidVersion;
    }

    private boolean checkForFlash(){
        if (!FlashApp.getInstance().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(FlashApp.getInstance(), "LED Flash not found", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void setFlashOn(boolean flashOn){
        this.isFlashOn = flashOn;
    }

    public boolean getFlashOn(){
        return isFlashOn;
    }

    public void setLedOn(boolean ledOn){
        this.ledOn = ledOn;
    }

    public boolean getLedOn(){
        return ledOn;
    }

    public String getHeadingDisplay() {
        return headingDisplay;
    }

    public void setHeadingDisplay(String headingDisplay) {
        this.headingDisplay = headingDisplay;
        SharedPreferencesHelper.addString(USER_STORAGE_FILE_NAME, USER_HEADING, headingDisplay);
    }

    public String getCompassRose(){
        return compassRose;
    }

    public void setCompassRose(String compassRose){
        this.compassRose = compassRose;
        SharedPreferencesHelper.addString(USER_STORAGE_FILE_NAME, USER_COMPASS_ROSE, compassRose);
    }

    public String getFlashMode(){
        return flashMode;
    }

    public void setFlashMode(String flashMode){
        this.flashMode = flashMode;
        SharedPreferencesHelper.addString(USER_STORAGE_FILE_NAME, USER_FLASH_MODE, flashMode);
    }

    public String getFlashInBackground(){
        return flashInBackground;
    }

    public void setFlashInBackground(String flashInBackground){
        this.flashInBackground = flashInBackground;
        SharedPreferencesHelper.addString(USER_STORAGE_FILE_NAME, USER_FLASH_IN_BACKGROUND, flashInBackground);
    }

    public String getTheme(){
        return theme;
    }

    public void setTheme(String theme){
        this.theme = theme;
        SharedPreferencesHelper.addString(USER_STORAGE_FILE_NAME, USER_THEME, theme);
    }

    public void setWakeLock(String string){
        wakeLock = string;
        SharedPreferencesHelper.addString(USER_STORAGE_FILE_NAME, USER_WAKE_LOCK, wakeLock);
    }

    public String getWakeLock(){
        return wakeLock;
    }
}