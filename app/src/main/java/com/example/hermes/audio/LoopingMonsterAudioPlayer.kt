package com.example.hermes.audio

import android.content.Context
import android.media.MediaPlayer

class LoopingMonsterAudioPlayer(applicationContext: Context, audioFile: Int) {
    var mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, audioFile)
    var isPlaying = false

    init {
        mediaPlayer.isLooping = true
        setVolume(0f)
    }

    fun setVolume(value: Float) {
        mediaPlayer.setVolume(value, value)
    }

    fun play() {
        mediaPlayer.start()
        isPlaying = true
    }
}