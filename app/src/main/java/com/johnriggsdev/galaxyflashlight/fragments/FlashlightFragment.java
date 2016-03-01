package com.johnriggsdev.galaxyflashlight.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.johnriggsdev.galaxyflashlight.R;
import com.johnriggsdev.galaxyflashlight.events.ScreenFlashEvent;
import com.johnriggsdev.galaxyflashlight.services.Camera2LedService;
import com.johnriggsdev.galaxyflashlight.services.CameraLedService;
import com.johnriggsdev.galaxyflashlight.models.User;
import com.johnriggsdev.galaxyflashlight.utils.BusDriver;
import com.johnriggsdev.galaxyflashlight.utils.ColorThemes;
import com.johnriggsdev.galaxyflashlight.utils.DialogHelper;

import static com.johnriggsdev.galaxyflashlight.utils.Constants.*;

public class FlashlightFragment extends Fragment {

    private Button buttonOff;
    private Button buttonOn;

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        buttonOff = (Button) view.findViewById(R.id.buttonFlashlightOff);
        buttonOn = (Button) view.findViewById(R.id.buttonFlashlightOn);

        if (User.getInstance().getAndroidVersion().equals(KITKAT)) {
            intent = new Intent(getActivity(), CameraLedService.class);
        } else {
            intent = new Intent(getActivity(), Camera2LedService.class);
        }

        buttonOff.setOnClickListener(new ButtonClickedListener());
        buttonOn.setOnClickListener(new ButtonClickedListener());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        buttonToFlashState();
        enforceFlashState(intent);
        ColorThemes.setButton(buttonOff, buttonOn, User.getInstance().getTheme());
    }

    class ButtonClickedListener implements CompoundButton.OnClickListener {

        @Override
        public void onClick(View v) {
            if (User.getInstance().getFlashOn()) {
                User.getInstance().setFlashOn(false);
                getActivity().stopService(intent);

                BusDriver.getBus().post(new ScreenFlashEvent(SCREEN_FLASH_OFF));

                buttonOn.setVisibility(View.GONE);
                buttonOff.setVisibility(View.VISIBLE);
            } else {
                String flashMode = User.getInstance().getFlashMode();

                User.getInstance().setFlashOn(true);


                if (flashMode.equals(FLASH_LED_ONLY) || flashMode.equals(FLASH_LED_AND_SCREEN)) {
                    if (!User.getInstance().getAndroidVersion().equals(MARSHMALLOW)){
                        getActivity().startService(intent);
                    } else {
                        if (hasCameraPermission()) {
                            getActivity().startService(intent);
                        } else {
                            getCameraPermission();
                        }
                    }
                }

                if (!flashMode.equals(FLASH_LED_ONLY)){
                    BusDriver.getBus().post(new ScreenFlashEvent(SCREEN_FLASH_ON));
                }

                buttonOff.setVisibility(View.GONE);
                buttonOn.setVisibility(View.VISIBLE);
            }
        }
    }

    private void buttonToFlashState(){
        if (User.getInstance().getFlashOn()) {
            buttonOff.setVisibility(View.GONE);
            buttonOn.setVisibility(View.VISIBLE);
        } else {
            buttonOn.setVisibility(View.GONE);
            buttonOff.setVisibility(View.VISIBLE);
        }
    }

    private void enforceFlashState(Intent intent){
        String flashMode = User.getInstance().getFlashMode();
        if ((flashMode.equals(FLASH_SCREEN_ONLY) || flashMode.equals(FLASH_SCREEN_ONLY_NO_LED))
                && User.getInstance().getLedOn()){
            getActivity().stopService(intent);
        } if (!(flashMode.equals(FLASH_SCREEN_ONLY) || flashMode.equals(FLASH_SCREEN_ONLY_NO_LED))
                && !User.getInstance().getLedOn() && User.getInstance().getFlashOn()){
            getActivity().startService(intent);
        }
    }

    /**
     * Check to make sure the app has CAMERA permission. Android 6+ allows users to selectively
     * disable permissions. Trying to access the camera without this permission crashes the app due
     * to Fatal Exception: java.lang.SecurityException Lacking privileges to access camera service
     */
    private boolean hasCameraPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);

        switch (permissionCheck){
            case (PackageManager.PERMISSION_GRANTED):
                return true;
            default:
                return false;
        }
    }

    @TargetApi(23)
    private void getCameraPermission(){
        if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
            DialogHelper.showDialog(getActivity(), getActivity().getResources().getString(R.string.camera_permission),
                    getActivity().getResources().getString(R.string.camera_permission_message),
                    getActivity().getResources().getString(R.string.ok), okListener,
                    getActivity().getResources().getString(R.string.cancel), null);
        } else {
            requestCameraPermission();
        }
    }

    @TargetApi(23)
    private void requestCameraPermission(){
        requestPermissions(new String[] {Manifest.permission.CAMERA},
                REQUEST_CODE_ASK_PERMISSIONS);
        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getActivity().startService(intent);
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.camera_permission_denied), Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), REQUEST_CODE_APP_SETTINGS);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case (REQUEST_CODE_APP_SETTINGS):
                if (hasCameraPermission()){
                    getActivity().startService(intent);
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.camera_permission_denied), Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                return;
        }
    }
}
