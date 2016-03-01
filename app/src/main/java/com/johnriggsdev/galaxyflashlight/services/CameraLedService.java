package com.johnriggsdev.galaxyflashlight.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.johnriggsdev.galaxyflashlight.models.User;

    /*
    * CameraLedService is for use by Android APIs prior to 21 (Lollipop).
    * Use Camera2LedService for API 21 and later.
    */

public class CameraLedService extends Service {

    private Camera camera;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        camera = Camera.open();
        final Camera.Parameters p = camera.getParameters();

        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();

        User.getInstance().setLedOn(true);
    }

    @Override
    public void onDestroy() {

        if (camera != null) {
            camera.release();
        }

        User.getInstance().setLedOn(false);

        super.onDestroy();
    }
}
