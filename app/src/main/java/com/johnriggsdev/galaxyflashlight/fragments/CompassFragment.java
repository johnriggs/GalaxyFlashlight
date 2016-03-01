package com.johnriggsdev.galaxyflashlight.fragments;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnriggsdev.galaxyflashlight.R;
import com.johnriggsdev.galaxyflashlight.models.User;
import com.johnriggsdev.galaxyflashlight.utils.ColorThemes;
import com.johnriggsdev.galaxyflashlight.utils.Constants;
import static com.johnriggsdev.galaxyflashlight.utils.Constants.*;
import com.johnriggsdev.galaxyflashlight.utils.Conversions;
import com.squareup.picasso.Picasso;

public class CompassFragment extends Fragment implements SensorEventListener{

    private ImageView compassImage;

    // record the compass picture angle turned
    private float currentDegree = 0f;
    private float sensorDegree = 0f;
    TextView compassHeading;

    // device sensor manager
    private SensorManager sensorManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_compass, container, false);

        compassImage = (ImageView) view.findViewById(R.id.imageViewCompass);

        // TextView that will what degree the user is heading
        compassHeading = (TextView) view.findViewById(R.id.compassHeading);

        // initialize your android device sensor capabilities
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

        //Set the user selected Compass Rose image and theme color
        if (User.getInstance().getCompassRose().equals(COMPASS_ROSE_BLOCK)) {
            Picasso.with(getActivity()).load(R.drawable.compass_rose_block_white).into(compassImage);
        } else {
            Picasso.with(getActivity()).load(R.drawable.compass_rose_script_white).into(compassImage);
        }

        ColorThemes.setImageView(compassImage, User.getInstance().getTheme());
        ColorThemes.setTextView(compassHeading, User.getInstance().getTheme());
    }

    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        sensorDegree = event.values[0];

        if (User.getInstance().getHeadingDisplay().equals(Constants.HEADING_AZIMUTH)) {
            compassHeading.setText("Heading: " + String.valueOf(Math.round(sensorDegree)) + getActivity().getResources().getString(R.string.degrees_symbol) + " (" + Conversions.azimuthToDirection(sensorDegree) + ")");
        } else {
            compassHeading.setText("Heading: " + Conversions.azimuthToQuadrant(sensorDegree));
        }

        // create a rotation animation (reverse turn sensorDegree degrees)
        RotateAnimation rotateAnimation = new RotateAnimation(
                currentDegree,
                -sensorDegree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        rotateAnimation.setDuration(210);

        // set the animation after the end of the reservation status
        rotateAnimation.setFillAfter(true);

        // Start the animation
        compassImage.startAnimation(rotateAnimation);
        currentDegree = -sensorDegree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
