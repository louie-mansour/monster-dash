package com.example.hermes.audio

import android.content.Context
import android.media.MediaPlayer

class NonLoopingMonsterAudioPlayer(applicationContext: Context, audioFile: Int) {
    var mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, audioFile)
    var lastPlayed: Int = 0

    init {
        mediaPlayer.isLooping = false
        mediaPlayer.setVolume(1f, 1f)
    }

    fun play(time: Int) {
        mediaPlayer.start()
        lastPlayed = time
    }
}