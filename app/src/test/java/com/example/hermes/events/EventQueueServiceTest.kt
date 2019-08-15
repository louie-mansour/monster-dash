package com.example.hermes.events

import android.media.MediaPlayer
import com.example.hermes.TestConfigs
import com.example.hermes.audio.ProgressAudioPlayerService
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class EventQueueServiceTest {

    private val progressAudioPlayerService: ProgressAudioPlayerService = Mockito.mock(ProgressAudioPlayerService::class.java)

    init {
        Mockito.`when`(progressAudioPlayerService.findAudioFile(Mockito.anyDouble()))
            .thenReturn(Pair(Mockito.mock(MediaPlayer::class.java), "name"))
    }

    private val eventQueueService = EventQueueService(progressAudioPlayerService)

    @Test
    fun oneMinuteRunWithTwelveSecondUpdates() {
        val testConfigs = testConfigsFactory(60, 12)
        val eventQueue = eventQueueService.eventQueueFactory(testConfigs)

        Assert.assertEquals(5, eventQueue.size)
        Assert.assertEquals(12, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(24, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(36, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(48, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(60, eventQueue.poll().timeElapsedInSeconds)
    }

    private fun testConfigsFactory(runTimeInSeconds: Int, timeBetweenUpdatesInSeconds: Int): TestConfigs {
        return TestConfigs(
            0,
            0,
            0,
            0f,
            0f,
            0f,
            runTimeInSeconds,
            timeBetweenUpdatesInSeconds
        )
    }
}