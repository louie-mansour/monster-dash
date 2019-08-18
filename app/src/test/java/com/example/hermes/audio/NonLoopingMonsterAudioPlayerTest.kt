package com.example.hermes.audio

import android.media.MediaPlayer
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class NonLoopingMonsterAudioPlayerTest {
    private val mediaPlayer = Mockito.mock(MediaPlayer::class.java)

    @Test
    fun isNotLoopingWithFullVolumeOnConstruction() {
        NonLoopingMonsterAudioPlayer(mediaPlayer)
        Mockito.verify(mediaPlayer, Mockito.times(1)).isLooping = false
        Mockito.verify(mediaPlayer, Mockito.times(1)).setVolume(1f, 1f)
        Mockito.verify(mediaPlayer, Mockito.times(0)).start()
    }

    @Test
    fun playStartsTheMediaPlayerAndRecordsTheTimeLastPlayed() {
        val nonLoopingMonsterAudioPlayer = NonLoopingMonsterAudioPlayer(mediaPlayer)
        Assert.assertEquals(0, nonLoopingMonsterAudioPlayer.lastPlayed)

        nonLoopingMonsterAudioPlayer.play(5)
        Mockito.verify(mediaPlayer, Mockito.times(1)).start()
        Mockito.verify(mediaPlayer, Mockito.times(1)).setVolume(1f, 1f)
        Assert.assertEquals(5, nonLoopingMonsterAudioPlayer.lastPlayed)
    }
}