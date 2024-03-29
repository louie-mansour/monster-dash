package com.molasys.hermes.events

import android.media.MediaPlayer
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock

class EventTest {
    @Test
    fun event_constructor() {
        val mediaPlayer = mock(MediaPlayer::class.java)
        val event = Event(0, mediaPlayer, "test")
        Assert.assertEquals(0, event.timeToPlay)
        Assert.assertEquals(mediaPlayer, event.audio)
        Assert.assertEquals("test", event.name)
    }
}