package com.molasys.hermes.events

import com.molasys.hermes.TestConfigs
import com.molasys.hermes.audio.ProgressAudioPlayerService
import java.util.*

class EventQueueService(private val progressAudioPlayerService: ProgressAudioPlayerService) {
    fun eventQueueFactory(testConfigs: TestConfigs): Queue<Event> {
        val eventQueue = PriorityQueue<Event>()
        val runLengthInSeconds = testConfigs.runTimeInSeconds
        var timeElapsedInSeconds = testConfigs.timeBetweenUpdatesInSeconds
        if(timeElapsedInSeconds <= 5) {
            throw IllegalArgumentException("timeBetweenUpdatesInSeconds must be greater than 5")
        }
        while(timeElapsedInSeconds <= runLengthInSeconds) {
            val (audioFile, audioFileName) = progressAudioPlayerService.findAudioFile(100 * timeElapsedInSeconds.toDouble() / runLengthInSeconds)

            eventQueue.add(
                Event(timeElapsedInSeconds, audioFile, audioFileName)
            )
            timeElapsedInSeconds += testConfigs.timeBetweenUpdatesInSeconds
        }
        return eventQueue
    }
}