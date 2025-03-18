package com.example.mediaapp

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var videoView: VideoView
    private lateinit var btnChooseAudio: Button
    private lateinit var btnChooseVideo: Button
    private lateinit var btnPlayAudio: Button
    private lateinit var btnPauseAudio: Button
    private lateinit var btnStopAudio: Button
    private lateinit var btnPlayVideo: Button
    private lateinit var btnPauseVideo: Button
    private lateinit var btnStopVideo: Button
    private lateinit var audioIcon: ImageView  // Changed from ImageButton to ImageView

    private var lastAudioUri: Uri? = null

    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            setupMediaPlayer(it)
            enableAudioControls(true)
            audioIcon.visibility = View.VISIBLE // Показуємо іконку для аудіо
            videoView.visibility = View.GONE // Ховаємо відео
        }
    }

    private val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            setupVideoView(it)
            enableVideoControls(true)
            videoView.visibility = View.VISIBLE // Показуємо відео
            audioIcon.visibility = View.GONE // Ховаємо іконку для аудіо
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnChooseAudio = findViewById(R.id.btnChooseAudio)
        btnChooseVideo = findViewById(R.id.btnChooseVideo)
        btnPlayAudio = findViewById(R.id.btnPlayAudio)
        btnPauseAudio = findViewById(R.id.btnPauseAudio)
        btnStopAudio = findViewById(R.id.btnStopAudio)
        btnPlayVideo = findViewById(R.id.btnPlayVideo)
        btnPauseVideo = findViewById(R.id.btnPauseVideo)
        btnStopVideo = findViewById(R.id.btnStopVideo)
        videoView = findViewById(R.id.videoView)
        audioIcon = findViewById(R.id.audioIcon)

        enableAudioControls(false)
        enableVideoControls(false)

        mediaPlayer = MediaPlayer()

        btnChooseAudio.setOnClickListener { pickAudioLauncher.launch("audio/*") }
        btnChooseVideo.setOnClickListener { pickVideoLauncher.launch("video/*") }

        btnPlayAudio.setOnClickListener { mediaPlayer.start() }
        btnPauseAudio.setOnClickListener { if (mediaPlayer.isPlaying) mediaPlayer.pause() }
        btnStopAudio.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                lastAudioUri?.let { setupMediaPlayer(it) }
                enableAudioControls(true)
            }
        }

        btnPlayVideo.setOnClickListener { videoView.start() }
        btnPauseVideo.setOnClickListener { if (videoView.isPlaying) videoView.pause() }
        btnStopVideo.setOnClickListener {
            videoView.stopPlayback()
            videoView.resume()
            enableVideoControls(false)
        }
    }

    private fun setupMediaPlayer(uri: Uri) {
        lastAudioUri = uri
        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(this@MainActivity, uri)
            prepare()
        }
    }

    private fun setupVideoView(uri: Uri) {
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { enableVideoControls(true) }
        videoView.requestFocus()
    }

    private fun enableAudioControls(enabled: Boolean) {
        btnPlayAudio.isEnabled = enabled
        btnPauseAudio.isEnabled = enabled
        btnStopAudio.isEnabled = enabled
    }

    private fun enableVideoControls(enabled: Boolean) {
        btnPlayVideo.isEnabled = enabled
        btnPauseVideo.isEnabled = enabled
        btnStopVideo.isEnabled = enabled
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
        if (videoView.isPlaying) videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
