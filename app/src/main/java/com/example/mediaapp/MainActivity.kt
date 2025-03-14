package com.example.mediaapp

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
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

    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            setupMediaPlayer(it)
            btnPlayAudio.isEnabled = true
            btnStopAudio.isEnabled = true
            btnPauseAudio.isEnabled = true
        }
    }

    private val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            setupVideoView(it)
            btnPlayVideo.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізація елементів UI
        btnChooseAudio = findViewById(R.id.btnChooseAudio)
        btnChooseVideo = findViewById(R.id.btnChooseVideo)
        btnPlayAudio = findViewById(R.id.btnPlayAudio)
        btnPauseAudio = findViewById(R.id.btnPauseAudio)
        btnStopAudio = findViewById(R.id.btnStopAudio)
        btnPlayVideo = findViewById(R.id.btnPlayVideo)
        videoView = findViewById(R.id.videoView)

        // Налаштування MediaPlayer для аудіо
        mediaPlayer = MediaPlayer()

        // Слухач для вибору аудіо
        btnChooseAudio.setOnClickListener {
            pickAudioLauncher.launch("audio/*")
        }

        // Слухач для вибору відео
        btnChooseVideo.setOnClickListener {
            pickVideoLauncher.launch("video/*")
        }

        // Слухач для відтворення аудіо
        btnPlayAudio.setOnClickListener {
            mediaPlayer.start()
        }

        // Слухач для паузи аудіо
        btnPauseAudio.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        // Слухач для зупинки аудіо
        btnStopAudio.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare()
            }
        }

        // Слухач для відтворення відео
        btnPlayVideo.setOnClickListener {
            videoView.start()
        }
    }

    // Метод для налаштування MediaPlayer для аудіо
    private fun setupMediaPlayer(uri: Uri) {
        mediaPlayer.apply {
            setDataSource(this@MainActivity, uri)
            prepare()
        }
    }

    // Метод для налаштування VideoView для відео
    private fun setupVideoView(uri: Uri) {
        videoView.apply {
            setVideoURI(uri)
            setOnPreparedListener {
                btnPlayVideo.isEnabled = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
    }
}
