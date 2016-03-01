package com.johnriggsdev.galaxyflashlight.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DialogHelper {

    public static void showDialog(final Activity activity, final String title,
                                  final String message, final String positiveText,
                                  final DialogInterface.OnClickListener positive,
                                  final String negativeText, final DialogInterface.OnClickListener negative) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try{
                    new AlertDialog.Builder(activity)
                            .setTitle(title)
                            .setMessage(message)
                            .setCancelable(false)
                            .setPositiveButton(positiveText, positive)
                            .setNegativeButton(negativeText, negative)
                            .create().show();
                }catch(Exception e){}
            }
        });
    }
}
