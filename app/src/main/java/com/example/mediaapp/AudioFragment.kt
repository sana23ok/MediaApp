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

    // Оголошуємо змінні для елементів інтерфейсу
    private lateinit var btnChooseAudio: Button  // Кнопка для вибору аудіофайлу
    private lateinit var mediaPlayer: MediaPlayer  // Медіаплеєр для відтворення аудіо
    private lateinit var btnPlayAudio: Button  // Кнопка для відтворення аудіо
    private lateinit var btnPauseAudio: Button  // Кнопка для паузи аудіо
    private lateinit var btnStopAudio: Button  // Кнопка для зупинки аудіо
    private lateinit var audioIcon: ImageView  // Іконка, що відображається при наявності аудіо
    private lateinit var btnSwitchToVideo: Button  // Кнопка для перемикання на відео формат

    private var lastAudioUri: Uri? = null  // Останній вибраний URI для аудіофайлу

    // Ініціалізація ActivityResultContract для вибору аудіофайлу
    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            setupMediaPlayer(it)  // Налаштувати медіаплеєр з обраним аудіо
            enableAudioControls(true)  // Увімкнути елементи керування аудіо
            audioIcon.visibility = View.VISIBLE  // Показати іконку аудіо
        }
    }

    // Метод, що створює вигляд фрагменту
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio, container, false)  // Інфлейтинг розмітки
    }

    // Метод, який викликається після створення вигляду фрагменту
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ініціалізація елементів інтерфейсу з використанням їх ID
        btnChooseAudio = view.findViewById(R.id.btnChooseAudio)
        btnPlayAudio = view.findViewById(R.id.btnPlayAudio)
        btnPauseAudio = view.findViewById(R.id.btnPauseAudio)
        btnStopAudio = view.findViewById(R.id.btnStopAudio)
        audioIcon = view.findViewById(R.id.audioIcon)
        btnSwitchToVideo = view.findViewById(R.id.btnSwitchToVideo)

        enableAudioControls(false)  // Спочатку відключаємо елементи керування

        mediaPlayer = MediaPlayer()  // Ініціалізація MediaPlayer

        // Налаштування кліка для вибору аудіофайлу
        btnChooseAudio.setOnClickListener {
            // Запускаємо контракт для вибору аудіофайлу
            pickAudioLauncher.launch("audio/*")
        }

        // Клікабельність кнопки відтворення аудіо
        btnPlayAudio.setOnClickListener { mediaPlayer.start() }
        // Клікабельність кнопки паузи аудіо
        btnPauseAudio.setOnClickListener { if (mediaPlayer.isPlaying) mediaPlayer.pause() }
        // Клікабельність кнопки зупинки аудіо
        btnStopAudio.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                lastAudioUri?.let { setupMediaPlayer(it) }  // Перезавантажити з останнім аудіофайлом
                enableAudioControls(true)
            }
        }

        // перемикання на фрагмент відео
        btnSwitchToVideo.setOnClickListener {
            (activity as MainActivity).showVideoFragment()
        }
    }

    // налаштування медіаплеєра
    private fun setupMediaPlayer(uri: Uri) {
        lastAudioUri = uri
        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(requireContext(), uri)
            prepare()
        }
    }

    // Метод для увімкнення або вимкнення елементів керування аудіо
    private fun enableAudioControls(enabled: Boolean) {
        btnPlayAudio.isEnabled = enabled
        btnPauseAudio.isEnabled = enabled
        btnStopAudio.isEnabled = enabled
    }
}
