package com.molasys.hermes.audio

import android.media.MediaPlayer

open class LoopingMonsterAudioPlayer(private var mediaPlayer: MediaPlayer) {

    init {
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(0f, 0f)
    }

    open fun setVolume(value: Float) {
        mediaPlayer.setVolume(value, value)
    }

    open fun play() {
        mediaPlayer.start()
    }
}