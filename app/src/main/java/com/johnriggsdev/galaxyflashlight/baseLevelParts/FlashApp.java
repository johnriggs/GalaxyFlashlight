package com.johnriggsdev.galaxyflashlight.baseLevelParts;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class FlashApp extends Application {
    private static FlashApp flashApp;

    @Override
    public void onCreate() {
        super.onCreate();
        flashApp = this;

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
    }

    public static FlashApp getInstance(){
        return flashApp;
    }
}
