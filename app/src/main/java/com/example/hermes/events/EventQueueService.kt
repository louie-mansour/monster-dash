package com.example.hermes.events

import com.example.hermes.TestConfigs
import com.example.hermes.audio.ProgressAudioPlayerService
import java.util.*

class EventQueueService(private val progressAudioPlayerService: ProgressAudioPlayerService) {
    fun eventQueueFactory(testConfigs: TestConfigs): Queue<Event> {
        val eventQueue = PriorityQueue<Event>()
        val runLengthInSeconds = testConfigs.runTimeInSeconds
        var timeElapsedInSeconds = testConfigs.timeBetweenUpdatesInSeconds
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