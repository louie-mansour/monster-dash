package com.example.hermes.audio

import android.content.Context
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class ProgressAudioPlayerServiceTest {

    private val dummyContext = Mockito.mock(Context::class.java)
    private val progressAudioPlayerService = ProgressAudioPlayerService(dummyContext)

    @Test
    fun findAudioFile() {
        val percentsComplete = (0..100 step 5).toList()
        val files = listOf(
            "one_percent_complete",
            "five_percent_complete",
            "ten_percent_complete",
            "fifteen_percent_complete",
            "twenty_percent_complete",
            "twenty_five_percent_complete",
            "thirty_percent_complete",
            "thirty_five_percent_complete",
            "forty_percent_complete",
            "forty_five_percent_complete",
            "fifty_percent_complete",
            "fifty_five_percent_complete",
            "sixty_percent_complete",
            "sixty_five_percent_complete",
            "seventy_percent_complete",
            "seventy_five_percent_complete",
            "eighty_percent_complete",
            "eighty_five_percent_complete",
            "ninety_percent_complete",
            "ninety_five_percent_complete",
            "one_hundred_percent_complete"
        )
        for(i in 0 until percentsComplete.size) {
            val (_, fileName) = progressAudioPlayerService.findAudioFile(percentsComplete[i].toDouble())
            Assert.assertEquals(files[i], fileName)
        }
    }
}