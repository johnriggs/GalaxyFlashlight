package com.johnriggsdev.galaxyflashlight.utils;

public class Constants {

    //General Settings
    public static final String WAKE_LOCK_ON = "wake_lock_on";
    public static final String WAKE_LOCK_OFF = "wake_lock_off";

    //Permissions Constant
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public static final int REQUEST_CODE_APP_SETTINGS = 234;

    //User Model Strings
    public static final String KITKAT = "kitkat";
    public static final String LOLLIPOP = "lollipop";
    public static final String MARSHMALLOW = "marshmallow";

    //Compass Settings
    public static final String COMPASS_ROSE_BLOCK = "compass_rose_block";
    public static final String COMPASS_ROSE_SCRIPT = "compass_rose_script";
    public static final String HEADING_AZIMUTH = "heading_azimuth";
    public static final String HEADING_QUADRANT = "heading_quadrant";
    public static final String THEME_BLUE = "theme_blue";
    public static final String THEME_GREEN = "theme_green";
    public static final String THEME_ORANGE = "theme_orange";
    public static final String THEME_PURPLE = "theme_purple";
    public static final String THEME_RED = "theme_red";
    public static final int COMPASS_HEIGHT = 650;
    public static final int COMPASS_WIDTH = 650;
    public static final int THUMBNAIL_HEIGHT = 250;
    public static final int THUMBNAIL_WIDTH = 250;

    //Flashlight Settings
    public static final String FLASH_LED_AND_SCREEN = "flash_led_and_screen";
    public static final String FLASH_LED_ONLY = "flash_led_only";
    public static final String FLASH_SCREEN_ONLY = "flash_screen_only";
    public static final String FLASH_SCREEN_ONLY_NO_LED = "flash_screen_only_no_led";
    public static final String FLASH_OFF_IN_BACKGROUND = "flash_off_in_background";
    public static final String FLASH_ON_IN_BACKGROUND = "flash_on_in_background";

    //Shared Preferences/Storage Constants
    public static final String USER_STORAGE_FILE_NAME = "flashlight_user";
    public static final String USER_ACCEPTED_TERMS = "user_accepted_terms";
    public static final String USER_COMPASS_ROSE = "user_compass_rose";
    public static final String USER_FLASH_MODE = "user_flash_mode";
    public static final String USER_FLASH_IN_BACKGROUND = "user_flash_on_in_background";
    public static final String USER_HEADING = "user_heading";
    public static final String USER_THEME = "user_theme";
    public static final String USER_WAKE_LOCK = "user_wake_lock";

    //ScreenFlashEvent strings
    public static final String SCREEN_FLASH_OFF = "screen_flash_off";
    public static final String SCREEN_FLASH_ON = "screen_flash_on";

    //Web URLs
    public static final String GOOGLE_CALIBRATION = "https://support.google.com/gmm/answer/6145351?hl=en";
    public static final String JRDCOM_TUTORIAL = "http://johnriggsdev.com/android-fundamentals-tutorial-compass-and-flashlight-app-part-1";
}
