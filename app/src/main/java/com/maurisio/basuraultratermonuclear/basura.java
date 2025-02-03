package com.maurisio.basuraultratermonuclear;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;
import android.view.TextureView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class basura{
    private void createCaptureSession(CameraDevice camera) {
        try {
            TextureView surfaceView = null;
            SurfaceTexture texture = surfaceView.getSurfaceTexture();  // Obtén un Surface de tu vista
            Surface surface = new Surface(texture);

            List<Surface> surfaces = new ArrayList<>();
            surfaces.add(surface);

            // Prepara la configuración de captura
            camera.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        // Comienza la captura
                        session.setRepeatingRequest(createCaptureRequest(camera), null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    // Maneja el fallo de configuración
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CaptureRequest createCaptureRequest(CameraDevice camera) {
        try {
            CaptureRequest.Builder builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            Surface surface = null;
            builder.addTarget(surface);
            return builder.build();
        } catch (CameraAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}