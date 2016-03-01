package com.johnriggsdev.galaxyflashlight.utils;

import android.graphics.PorterDuff;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnriggsdev.galaxyflashlight.R;
import com.johnriggsdev.galaxyflashlight.baseLevelParts.FlashApp;
import static com.johnriggsdev.galaxyflashlight.utils.Constants.*;

public class ColorThemes {

    /*
     * Note on setImageView:
     * For proper color output when calling setColorFilter for the ImageView, the drawable .png
     * should be white on transparency. Other colors will multiply accordingly but will not produce
     * a drawable of the desired color.
     */
    public static void setImageView(ImageView imageView, String theme){
        if (theme.equals(THEME_PURPLE)){
            imageView.setColorFilter(FlashApp.getInstance().getResources().getColor(R.color.purple), PorterDuff.Mode.MULTIPLY);
        } else if (theme.equals(THEME_BLUE)){
            imageView.setColorFilter(FlashApp.getInstance().getResources().getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);
        } else if (theme.equals(THEME_GREEN)){
            imageView.setColorFilter(FlashApp.getInstance().getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
        } else if (theme.equals(THEME_ORANGE)){
            imageView.setColorFilter(FlashApp.getInstance().getResources().getColor(R.color.orange), PorterDuff.Mode.MULTIPLY);
        } else {
            imageView.setColorFilter(FlashApp.getInstance().getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
        }
    }
    
    public static void setButton(Button buttonOff, Button buttonOn, String theme){
        if (theme.equals(THEME_PURPLE)){
            buttonOff.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.purple_dark));
            buttonOn.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.purple_light));
        } else if (theme.equals(THEME_BLUE)){
            buttonOff.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.blue_dark));
            buttonOn.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.blue_light));
        } else if (theme.equals(THEME_GREEN)){
            buttonOff.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.green_dark));
            buttonOn.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.green_light));
        } else if (theme.equals(THEME_ORANGE)){
            buttonOff.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.orange_dark));
            buttonOn.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.orange_light));
        } else {
            buttonOff.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.red_dark));
            buttonOn.setBackgroundColor(FlashApp.getInstance().getResources().getColor(R.color.red_light));
        }
    }

    public static void setTextView(TextView textView, String theme){
        if (theme.equals(THEME_PURPLE)){
            textView.setTextColor(FlashApp.getInstance().getResources().getColor(R.color.purple_dark));
        } else if (theme.equals(THEME_BLUE)){
            textView.setTextColor(FlashApp.getInstance().getResources().getColor(R.color.blue_dark));
        } else if (theme.equals(THEME_GREEN)){
            textView.setTextColor(FlashApp.getInstance().getResources().getColor(R.color.green_dark));
        } else if (theme.equals(THEME_ORANGE)){
            textView.setTextColor(FlashApp.getInstance().getResources().getColor(R.color.orange_dark));
        } else {
            textView.setTextColor(FlashApp.getInstance().getResources().getColor(R.color.red_dark));
        }
    }
}
