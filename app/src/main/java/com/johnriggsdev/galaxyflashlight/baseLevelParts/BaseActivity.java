package com.johnriggsdev.galaxyflashlight.baseLevelParts;

import android.app.Activity;
import android.content.Intent;

import com.johnriggsdev.galaxyflashlight.activities.MainActivity;
import com.johnriggsdev.galaxyflashlight.models.User;
import com.johnriggsdev.galaxyflashlight.services.Camera2LedService;
import com.johnriggsdev.galaxyflashlight.services.CameraLedService;

import static com.johnriggsdev.galaxyflashlight.utils.Constants.FLASH_OFF_IN_BACKGROUND;
import static com.johnriggsdev.galaxyflashlight.utils.Constants.KITKAT;

public class BaseActivity extends Activity {

    @Override
    protected void onPause() {
        super.onPause();

        //Controls LED flash regardless of activity if the app gets put in background
        if (User.getInstance().getLedOn() && User.getInstance().getFlashInBackground().equals(FLASH_OFF_IN_BACKGROUND)) {
            if (User.getInstance().getAndroidVersion().equals(KITKAT)) {
                Intent intent = new Intent(BaseActivity.this, CameraLedService.class);
                stopService(intent);
            } else {
                Intent intent = new Intent(BaseActivity.this, Camera2LedService.class);
                stopService(intent);
            }
        }
    }
}
