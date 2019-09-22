package com.molasys.hermes.audio

import android.media.MediaPlayer
import org.junit.Test
import org.mockito.Mockito

class LoopingMonsterAudioPlayerPlayerTest {
    private val mediaPlayer = Mockito.mock(MediaPlayer::class.java)

    @Test
    fun isLoopingWithNoVolumeOnConstruction() {
        LoopingMonsterAudioPlayer(mediaPlayer)
        Mockito.verify(mediaPlayer, Mockito.times(1)).isLooping = true
        Mockito.verify(mediaPlayer, Mockito.times(1)).setVolume(0f, 0f)
        Mockito.verify(mediaPlayer, Mockito.times(0)).start()
    }

    @Test
    fun setVolumeChangesVolumeOfBothLeftAndRight() {
        val loopingMonsterAudioVolume = LoopingMonsterAudioPlayer(mediaPlayer)
        loopingMonsterAudioVolume.setVolume(0.5f)
        Mockito.verify(mediaPlayer, Mockito.times(1)).setVolume(0.5f, 0.5f)
        Mockito.verify(mediaPlayer, Mockito.times(0)).start()
    }

    @Test
    fun playStartsTheMediaPlayerAndDoesNotChangeVolume() {
        val loopingMonsterAudioVolume = LoopingMonsterAudioPlayer(mediaPlayer)
        loopingMonsterAudioVolume.play()
        Mockito.verify(mediaPlayer, Mockito.times(1)).start()
        Mockito.verify(mediaPlayer, Mockito.times(1)).setVolume(0f, 0f)
    }
}