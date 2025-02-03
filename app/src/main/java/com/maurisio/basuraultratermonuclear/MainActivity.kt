package com.maurisio.basuraultratermonuclear

import CameraPreview
import WebRTCStreamer
import android.content.pm.PackageManager
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maurisio.basuraultratermonuclear.ui.theme.BasuraUltraTermoNuclearTheme
import org.webrtc.Camera2Capturer
import org.webrtc.CameraVideoCapturer
import org.webrtc.VideoCapturer

class MainActivity : ComponentActivity() {
    private lateinit var cameraPreview: CameraPreview
    private lateinit var webRTCStreamer: WebRTCStreamer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // pide permiso para usar la cam
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 100)
        }

        enableEdgeToEdge()
        setContent {
            BasuraUltraTermoNuclearTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // pues se pone la camara pa ver
        cameraPreview = CameraPreview(this)
        setContentView(cameraPreview)

//        val videoCapturer = createCameraCapturer()
//        webRTCStreamer.startStreaming(videoCapturer)
//
//        setContentView(webRTCStreamer)
    }

    private fun createCameraCapturer(): VideoCapturer {
        return Camera2Capturer(this, "0", object : CameraVideoCapturer.CameraEventsHandler {
            override fun onCameraError(p0: String?) {}
            override fun onCameraDisconnected() {}
            override fun onCameraFreezed(p0: String?) {}
            override fun onCameraOpening(p0: String?) {}
            override fun onFirstFrameAvailable() {}
            override fun onCameraClosed() {}
        })
    }

    override fun onPause() {
        super.onPause()
        cameraPreview.soltarCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraPreview.soltarCamera()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasuraUltraTermoNuclearTheme {
        Greeting("Android")
    }
}