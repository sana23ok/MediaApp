package com.example.mediaapp

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class AudioFragment : Fragment() {

    private lateinit var btnChooseAudio: Button
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var btnPlayAudio: Button
    private lateinit var btnPauseAudio: Button
    private lateinit var btnStopAudio: Button
    private lateinit var audioIcon: ImageView
    private lateinit var btnSwitchToVideo: Button  // Кнопка для перемикання на відео формат

    private var lastAudioUri: Uri? = null

    // Initialize the ActivityResultContract before the view is created
    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            setupMediaPlayer(it)
            enableAudioControls(true)
            audioIcon.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnChooseAudio = view.findViewById(R.id.btnChooseAudio)
        btnPlayAudio = view.findViewById(R.id.btnPlayAudio)
        btnPauseAudio = view.findViewById(R.id.btnPauseAudio)
        btnStopAudio = view.findViewById(R.id.btnStopAudio)
        audioIcon = view.findViewById(R.id.audioIcon)
        btnSwitchToVideo = view.findViewById(R.id.btnSwitchToVideo)  // Ініціалізація кнопки

        enableAudioControls(false)

        mediaPlayer = MediaPlayer()

        btnChooseAudio.setOnClickListener {
            // Launch the activity result contract to choose an audio
            pickAudioLauncher.launch("audio/*")
        }

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

        // Перехід до відео фрагменту
        btnSwitchToVideo.setOnClickListener {
            (activity as MainActivity).showVideoFragment()
        }
    }

    private fun setupMediaPlayer(uri: Uri) {
        lastAudioUri = uri
        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(requireContext(), uri)
            prepare()
        }
    }

    private fun enableAudioControls(enabled: Boolean) {
        btnPlayAudio.isEnabled = enabled
        btnPauseAudio.isEnabled = enabled
        btnStopAudio.isEnabled = enabled
    }
}

