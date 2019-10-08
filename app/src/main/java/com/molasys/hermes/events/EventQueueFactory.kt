package com.molasys.hermes.events

import com.molasys.hermes.TestConfigs
import com.molasys.hermes.audio.ProgressAudioService
import java.util.*

class EventQueueFactory(private val progressAudioService: ProgressAudioService) {
    fun make(testConfigs: TestConfigs): Queue<Event> {
        val eventQueue = PriorityQueue<Event>()
        val runLengthInSeconds = testConfigs.runTimeInSeconds
        var timeElapsedInSeconds = testConfigs.timeBetweenUpdatesInSeconds
        if(timeElapsedInSeconds <= 5) {
            throw IllegalArgumentException("timeBetweenUpdatesInSeconds must be greater than 5 seconds")
        }
        while(timeElapsedInSeconds <= runLengthInSeconds) {
            val (audioFile, audioFileName) = progressAudioService.findAudioFile(100 * timeElapsedInSeconds.toDouble() / runLengthInSeconds)

            eventQueue.add(
                Event(timeElapsedInSeconds, audioFile, audioFileName)
            )
            timeElapsedInSeconds += testConfigs.timeBetweenUpdatesInSeconds
        }
        return eventQueue
    }
}