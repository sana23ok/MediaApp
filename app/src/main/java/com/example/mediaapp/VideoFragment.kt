package com.example.mediaapp

import android.net.Uri
import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class VideoFragment : Fragment() {

    private lateinit var btnChooseVideo: Button
    private lateinit var videoView: VideoView
    private lateinit var btnPlayVideo: Button
    private lateinit var btnPauseVideo: Button
    private lateinit var btnStopVideo: Button
    private lateinit var btnSwitchToAudio: Button  // Кнопка для перемикання на аудіо формат

    // Контракт для вибору відео
    private val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            setupVideoView(it)
            enableVideoControls(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ініціалізація елементів інтерфейсу
        videoView = view.findViewById(R.id.videoView)
        btnChooseVideo = view.findViewById(R.id.btnChooseVideo)
        btnPlayVideo = view.findViewById(R.id.btnPlayVideo)
        btnPauseVideo = view.findViewById(R.id.btnPauseVideo)
        btnStopVideo = view.findViewById(R.id.btnStopVideo)
        btnSwitchToAudio = view.findViewById(R.id.btnSwitchToAudio)

        enableVideoControls(false)  // Спочатку вимикаємо кнопки

        // Запуск вибору відео
        btnChooseVideo.setOnClickListener {
            pickVideoLauncher.launch("video/*")
        }

        // Кнопка для відтворення відео
        btnPlayVideo.setOnClickListener {
            if (!videoView.isPlaying) videoView.start()
        }

        // Кнопка для паузи відео
        btnPauseVideo.setOnClickListener {
            if (videoView.isPlaying) videoView.pause()
        }

        // Кнопка для зупинки відео
        btnStopVideo.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.stopPlayback()
                videoView.resume()  // Дозволяє відтворити відео знову
            }
            enableVideoControls(true)
        }

        // Перехід до фрагменту з аудіо
        btnSwitchToAudio.setOnClickListener {
            (activity as MainActivity).showAudioFragment()
        }
    }

    // Налаштування VideoView для відтворення відео
    private fun setupVideoView(uri: Uri) {
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { enableVideoControls(true) }
        videoView.requestFocus()
    }

    // Увімкнення/вимкнення кнопок керування відео
    private fun enableVideoControls(enabled: Boolean) {
        btnPlayVideo.isEnabled = enabled
        btnPauseVideo.isEnabled = enabled
        btnStopVideo.isEnabled = enabled
    }
}
