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
            .thenReturn(Pair(Mockito.mock(MediaPlayer::class.java), "dummyName"))
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

    @Test
    fun fiveMinuteRunWithThirtySecondUpdates() {
        val testConfigs = testConfigsFactory(300, 30)
        val eventQueue = eventQueueService.eventQueueFactory(testConfigs)

        Assert.assertEquals(10, eventQueue.size)
        Assert.assertEquals(30, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(60, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(90, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(120, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(150, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(180, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(210, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(240, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(270, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(300, eventQueue.poll().timeElapsedInSeconds)
    }

    @Test
    fun thirtyMinuteRunWithThirtySecondUpdates() {
        val testConfigs = testConfigsFactory(1800, 60)
        val eventQueue = eventQueueService.eventQueueFactory(testConfigs)

        Assert.assertEquals(30, eventQueue.size)
        Assert.assertEquals(60, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(120, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(180, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(240, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(300, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(360, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(420, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(480, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(540, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(600, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(660, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(720, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(780, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(840, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(900, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(960, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1020, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1080, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1140, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1200, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1260, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1320, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1380, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1440, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1500, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1560, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1620, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1680, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1740, eventQueue.poll().timeElapsedInSeconds)
        Assert.assertEquals(1800, eventQueue.poll().timeElapsedInSeconds)
    }

    @Test
    fun runTimeOfZeroSeconds() {
        val testConfigs = testConfigsFactory(0, 30)
        val eventQueue = eventQueueService.eventQueueFactory(testConfigs)
        Assert.assertEquals(0, eventQueue.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun timeBetweenUpdatesOfLessThanFive() {
        val testConfigs = testConfigsFactory(300, 3)
        eventQueueService.eventQueueFactory(testConfigs)
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