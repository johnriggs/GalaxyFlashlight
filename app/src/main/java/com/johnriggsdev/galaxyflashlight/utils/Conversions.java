package com.johnriggsdev.galaxyflashlight.utils;

import com.johnriggsdev.galaxyflashlight.R;
import com.johnriggsdev.galaxyflashlight.baseLevelParts.FlashApp;

public class Conversions {

    public static String azimuthToDirection(float degree){
        String direction = "";

        if ((degree <= 11.25) || (degree >= 348.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.north);
        } else if ((degree > 11.25) && (degree < 33.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.north_northeast);
        } else if ((degree >= 33.75) && (degree <= 56.25)){
            direction = FlashApp.getInstance().getResources().getString(R.string.northeast);
        } else if ((degree > 56.25) && (degree < 78.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.east_northeast);
        } else if ((degree >= 78.75) && (degree <= 101.25)){
            direction = FlashApp.getInstance().getResources().getString(R.string.east);
        } else if ((degree > 101.25) && (degree < 123.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.east_southeast);
        } else if ((degree >= 123.75) && (degree <= 146.25)){
            direction = FlashApp.getInstance().getResources().getString(R.string.southeast);
        } else if ((degree > 146.25) && (degree < 168.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.south_southeast);
        } else if ((degree >= 168.75) && (degree <= 191.25)){
            direction = FlashApp.getInstance().getResources().getString(R.string.south);
        } else if ((degree > 191.25) && (degree < 213.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.south_southwest);
        } else if ((degree >= 213.75) && (degree <= 236.25)){
            direction = FlashApp.getInstance().getResources().getString(R.string.southwest);
        } else if ((degree > 236.25) && (degree < 258.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.west_southwest);
        } else if ((degree >= 258.75) && (degree <= 281.25)){
            direction = FlashApp.getInstance().getResources().getString(R.string.west);
        } else if ((degree > 281.25) && (degree < 303.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.west_northwest);
        } else if ((degree >= 303.75) && (degree <= 326.25)){
            direction = FlashApp.getInstance().getResources().getString(R.string.northwest);
        } else if ((degree > 326.25) && (degree < 348.75)){
            direction = FlashApp.getInstance().getResources().getString(R.string.north_northwest);
        }

        return direction;
    }

    public static String azimuthToQuadrant(float degree){
        int degrees = Math.round(degree);
        String direction = "";

        if ((degrees == 360) || (degrees == 0)){
            direction = FlashApp.getInstance().getResources().getString(R.string.due_north);
        } else if (degrees == 90){
            direction = FlashApp.getInstance().getResources().getString(R.string.due_east);
        } else if (degrees == 180){
            direction = FlashApp.getInstance().getResources().getString(R.string.due_south);
        } else if (degrees == 270){
            direction = FlashApp.getInstance().getResources().getString(R.string.due_west);
        } else if ((degrees > 0) && (degrees < 90)){
            direction = FlashApp.getInstance().getResources().getString(R.string.north) + " " + degrees + FlashApp.getInstance().getResources().getString(R.string.degrees_symbol) + " " + FlashApp.getInstance().getResources().getString(R.string.east);
        } else if ((degrees > 90) && (degrees < 180)){
            direction = FlashApp.getInstance().getResources().getString(R.string.south) + " " + (180 - degrees) + FlashApp.getInstance().getResources().getString(R.string.degrees_symbol) + " " + FlashApp.getInstance().getResources().getString(R.string.east);
        } else if ((degrees > 270) && (degrees < 360)){
            direction = FlashApp.getInstance().getResources().getString(R.string.north) + " " + (360 - degrees) + FlashApp.getInstance().getResources().getString(R.string.degrees_symbol) + " " + FlashApp.getInstance().getResources().getString(R.string.west);
        } else if ((degrees > 180) && (degrees < 270)){
            direction = FlashApp.getInstance().getResources().getString(R.string.south) + " " + (degrees - 180) + FlashApp.getInstance().getResources().getString(R.string.degrees_symbol) + " " + FlashApp.getInstance().getResources().getString(R.string.west);
        }

        return direction;
    }
}
