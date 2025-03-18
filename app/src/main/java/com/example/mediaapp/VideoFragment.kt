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

    // Declare the pickVideoLauncher at the fragment level (before onViewCreated)
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

        videoView = view.findViewById(R.id.videoView)
        btnChooseVideo = view.findViewById(R.id.btnChooseVideo)
        btnPlayVideo = view.findViewById(R.id.btnPlayVideo)
        btnPauseVideo = view.findViewById(R.id.btnPauseVideo)
        btnStopVideo = view.findViewById(R.id.btnStopVideo)
        btnSwitchToAudio = view.findViewById(R.id.btnSwitchToAudio)  // Ініціалізація кнопки

        enableVideoControls(false)

        btnChooseVideo.setOnClickListener {
            // Launch the video picker
            pickVideoLauncher.launch("video/*")
        }

        btnPlayVideo.setOnClickListener {
            if (!videoView.isPlaying) {
                videoView.start()
            }
        }

        btnPauseVideo.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            }
        }

        btnStopVideo.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.stopPlayback()
                videoView.resume()
            }
            // Allow to play video again after stopping
            enableVideoControls(true)
        }

        // Перехід до аудіо фрагменту
        btnSwitchToAudio.setOnClickListener {
            (activity as MainActivity).showAudioFragment()
        }
    }

    private fun setupVideoView(uri: Uri) {
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { enableVideoControls(true) }
        videoView.requestFocus()
    }

    private fun enableVideoControls(enabled: Boolean) {
        btnPlayVideo.isEnabled = enabled
        btnPauseVideo.isEnabled = enabled
        btnStopVideo.isEnabled = enabled
    }
}
