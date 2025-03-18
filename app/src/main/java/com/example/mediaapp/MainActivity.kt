package com.example.mediaapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var btnChooseFile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnChooseFile = findViewById(R.id.btnChooseFile)

        btnChooseFile.setOnClickListener {
            showFileChooserDialog()
        }
    }

    private fun showFileChooserDialog() {
        val options = arrayOf("Audio", "Video")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Format")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showAudioFragment()  // Select Audio
                    1 -> showVideoFragment()  // Select Video
                }
            }
            .show()
    }

    internal fun showAudioFragment() {
        val audioFragment = AudioFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, audioFragment)
            .addToBackStack(null)
            .commit()
    }

    internal fun showVideoFragment() {
        val videoFragment = VideoFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, videoFragment)
            .addToBackStack(null)
            .commit()
    }
}




//class MainActivity : AppCompatActivity() {
//
//    private lateinit var mediaPlayer: MediaPlayer
//    private lateinit var videoView: VideoView
//    private lateinit var btnChooseFile: Button  // Одна кнопка для вибору формату
//    private lateinit var btnPlayAudio: Button
//    private lateinit var btnPauseAudio: Button
//    private lateinit var btnStopAudio: Button
//    private lateinit var btnPlayVideo: Button
//    private lateinit var btnPauseVideo: Button
//    private lateinit var btnStopVideo: Button
//    private lateinit var audioIcon: ImageView
//
//    private var lastAudioUri: Uri? = null
//
//    // Оновлений лончер для вибору аудіо
//    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let {
//            setupMediaPlayer(it)
//            enableAudioControls(true)
//            audioIcon.visibility = View.VISIBLE // Show audio icon
//            videoView.visibility = View.GONE // Hide video view
//        }
//    }
//
//    // Оновлений лончер для вибору відео
//    private val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let {
//            setupVideoView(it)
//            enableVideoControls(true)
//            videoView.visibility = View.VISIBLE // Show video view
//            audioIcon.visibility = View.GONE // Hide audio icon
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Ініціалізація кнопок та елементів інтерфейсу
//        btnChooseFile = findViewById(R.id.btnChooseFile)  // Кнопка для вибору формату
//        btnPlayAudio = findViewById(R.id.btnPlayAudio)
//        btnPauseAudio = findViewById(R.id.btnPauseAudio)
//        btnStopAudio = findViewById(R.id.btnStopAudio)
//        btnPlayVideo = findViewById(R.id.btnPlayVideo)
//        btnPauseVideo = findViewById(R.id.btnPauseVideo)
//        btnStopVideo = findViewById(R.id.btnStopVideo)
//        videoView = findViewById(R.id.videoView)
//        audioIcon = findViewById(R.id.audioIcon)
//
//        // Відключити елементи управління на старті
//        enableAudioControls(false)
//        enableVideoControls(false)
//
//        mediaPlayer = MediaPlayer()
//
//        // Кнопка для вибору формату
//        btnChooseFile.setOnClickListener { showFileChooserDialog() }
//
//        // Управління аудіо
//        btnPlayAudio.setOnClickListener { mediaPlayer.start() }
//        btnPauseAudio.setOnClickListener { if (mediaPlayer.isPlaying) mediaPlayer.pause() }
//        btnStopAudio.setOnClickListener {
//            if (mediaPlayer.isPlaying) {
//                mediaPlayer.stop()
//                mediaPlayer.reset()
//                lastAudioUri?.let { setupMediaPlayer(it) }
//                enableAudioControls(true)
//            }
//        }
//
//        // Управління відео
//        btnPlayVideo.setOnClickListener { videoView.start() }
//        btnPauseVideo.setOnClickListener { if (videoView.isPlaying) videoView.pause() }
//        btnStopVideo.setOnClickListener {
//            videoView.stopPlayback()
//            videoView.resume()
//            enableVideoControls(false)
//        }
//    }
//
//    // Метод для налаштування MediaPlayer для аудіо
//    private fun setupMediaPlayer(uri: Uri) {
//        lastAudioUri = uri
//        mediaPlayer.reset()
//        mediaPlayer.apply {
//            setDataSource(this@MainActivity, uri)
//            prepare()
//        }
//    }
//
//    // Метод для налаштування VideoView для відео
//    private fun setupVideoView(uri: Uri) {
//        videoView.setVideoURI(uri)
//        videoView.setOnPreparedListener { enableVideoControls(true) }
//        videoView.requestFocus()
//    }
//
//    // Метод для вибору формату (аудіо або відео)
//    private fun showFileChooserDialog() {
//        val options = arrayOf("Аудіо", "Відео")
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Вибір формату")
//            .setItems(options) { _, which ->
//                when (which) {
//                    0 -> pickAudioLauncher.launch("audio/*") // Вибір аудіо
//                    1 -> pickVideoLauncher.launch("video/*") // Вибір відео
//                }
//            }
//            .show()
//    }
//
//    private fun enableAudioControls(enabled: Boolean) {
//        btnPlayAudio.isEnabled = enabled
//        btnPauseAudio.isEnabled = enabled
//        btnStopAudio.isEnabled = enabled
//    }
//
//    private fun enableVideoControls(enabled: Boolean) {
//        btnPlayVideo.isEnabled = enabled
//        btnPauseVideo.isEnabled = enabled
//        btnStopVideo.isEnabled = enabled
//    }
//
//    override fun onStop() {
//        super.onStop()
//        if (mediaPlayer.isPlaying) mediaPlayer.pause()
//        if (videoView.isPlaying) videoView.pause()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mediaPlayer.release()
//    }
//}

