package com.johnriggsdev.galaxyflashlight.utils;

import com.johnriggsdev.galaxyflashlight.baseLevelParts.FlashApp;

public class SharedPreferencesHelper {
    public static void addString(String preferenceFile, String key, String value) {
        FlashApp.getInstance().getSharedPreferences(preferenceFile, 0)
                .edit()
                .putString(key, value)
                .commit();
    }

    public static String getString(String preferenceFile, String key) {
        return FlashApp.getInstance().getSharedPreferences(preferenceFile, 0).getString(key,"");
    }

    public static void addBoolean(String preferenceFile, String key, boolean value) {
        FlashApp.getInstance().getSharedPreferences(preferenceFile, 0)
                .edit()
                .putBoolean(key, value)
                .commit();
    }

    public static boolean getBoolean(String preferenceFile, String key) {
        return FlashApp.getInstance().getSharedPreferences(preferenceFile, 0).getBoolean(key, false);
    }

    public static boolean getBooleanDefaultTrue(String preferenceFile, String key) {
        return FlashApp.getInstance().getSharedPreferences(preferenceFile, 0).getBoolean(key, true);
    }

    public static void clearSharedPreferences(String preferenceFile) {
        FlashApp.getInstance().getSharedPreferences(preferenceFile, 0).edit().clear().commit();
    }
}
