package com.molasys.hermes.audio

import android.media.MediaPlayer

open class LoopingAudio(private var mediaPlayer: MediaPlayer) {

    init {
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(0f, 0f)
    }

    fun setVolume(value: Float) {
        mediaPlayer.setVolume(value, value)
    }

    fun play() {
        mediaPlayer.start()
    }

    fun stop() {
        mediaPlayer.stop()
    }
}