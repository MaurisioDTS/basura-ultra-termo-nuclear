import android.content.Context
import org.webrtc.*

class WebRTCStreamer(private val context: Context) {
    private val peerConnectionFactory: PeerConnectionFactory
    private lateinit var videoSource: VideoSource
    private lateinit var videoTrack: VideoTrack

    init {
        val initializationOptions = PeerConnectionFactory.InitializationOptions.builder(context)
            .setEnableInternalTracer(true)
            .createInitializationOptions()
        PeerConnectionFactory.initialize(initializationOptions)

        val options = PeerConnectionFactory.Options()
        peerConnectionFactory = PeerConnectionFactory.builder()
            .setOptions(options)
            .createPeerConnectionFactory()
    }

    fun startStreaming(videoCapturer: VideoCapturer) {
        val surfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", EglBase.create().eglBaseContext)

        videoSource = peerConnectionFactory.createVideoSource(videoCapturer.isScreencast)

        // ojo porque ese "context" viene del main activity, ese parametro se guarda en la clase y se usa aqui
        videoCapturer.initialize(surfaceTextureHelper, context, videoSource.capturerObserver)

        videoCapturer.startCapture(1280, 720, 60)

        videoTrack = peerConnectionFactory.createVideoTrack("video", videoSource)
    }

    fun stopStreaming() {
        videoSource.dispose()
    }
}
