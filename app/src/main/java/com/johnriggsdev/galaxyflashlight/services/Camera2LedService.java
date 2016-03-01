package com.johnriggsdev.galaxyflashlight.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Size;
import android.view.Surface;
import android.widget.Toast;

import com.johnriggsdev.galaxyflashlight.models.User;
import static com.johnriggsdev.galaxyflashlight.utils.Constants.*;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class Camera2LedService extends Service {
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    private CameraDevice cameraDevice;
    private CameraManager cameraManager;

    private SurfaceTexture surfaceTexture;
    private Surface surface;

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

        try {
            init();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close();
        User.getInstance().setLedOn(false);

    }

    @SuppressWarnings("ResourceType")
    private void init() throws CameraAccessException {
        cameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);

//        //here to check if flash is available
        if (!User.getInstance().getFlashMode().equals(FLASH_SCREEN_ONLY_NO_LED)) {
            cameraManager.openCamera("0", new CameraStateCallback(), null);
        } else {
            Toast.makeText(this, "Flash not available", Toast.LENGTH_SHORT).show();
        }
//        cameraManager.openCamera("0", new CameraStateCallback(), null);
    }


    // camera device callback
    class CameraStateCallback extends CameraDevice.StateCallback {

        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            //get builder
            try {
                captureRequestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                //flash default is off
                captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO);
                captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                List<Surface> list = new ArrayList<Surface>();
                surfaceTexture = new SurfaceTexture(1);
                Size size = getSmallestSize(cameraDevice.getId());
                surfaceTexture.setDefaultBufferSize(size.getWidth(), size.getHeight());
                surface = new Surface(surfaceTexture);
                list.add(surface);
                captureRequestBuilder.addTarget(surface);
                camera.createCaptureSession(list, new CameraCaptureSessionCallback(), null);

                User.getInstance().setLedOn(true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    }

    private Size getSmallestSize(String cameraId) throws CameraAccessException {
        Size[] outputSizes = cameraManager.getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                .getOutputSizes(SurfaceTexture.class);
        if (outputSizes == null || outputSizes.length == 0) {
            throw new IllegalStateException(
                    "Camera " + cameraId + "doesn't support any outputSize.");
        }
        Size chosen = outputSizes[0];
        for (Size s : outputSizes) {
            if (chosen.getWidth() >= s.getWidth() && chosen.getHeight() >= s.getHeight()) {
                chosen = s;
            }
        }
        return chosen;
    }

    //session callback
    class CameraCaptureSessionCallback extends CameraCaptureSession.StateCallback {

        @Override
        public void onConfigured(CameraCaptureSession session) {
            cameraCaptureSession = session;
            try {
                cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    }

    private void close() {
        if (cameraDevice == null || cameraCaptureSession == null) {
            return;
        }
        cameraCaptureSession.close();
        cameraDevice.close();
        cameraDevice = null;
        cameraCaptureSession = null;
    }
}
