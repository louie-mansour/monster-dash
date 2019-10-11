package com.molasys.hermes.events

import com.molasys.hermes.TestConfigs
import com.molasys.hermes.audio.NonLoopingAudio
import com.molasys.hermes.audio.ProgressAudioService
import java.util.*

class EventQueueFactory(private val progressAudioService: ProgressAudioService) {
    fun make(testConfigs: TestConfigs): EventQueue {
        val eventQueue = EventQueue()
        val runLengthInSeconds = testConfigs.runTimeInSeconds
        var timeElapsedInSeconds = testConfigs.timeBetweenUpdatesInSeconds

        while(timeElapsedInSeconds <= runLengthInSeconds) {
            val (audioFile, audioFileName) = progressAudioService.findAudioFile(100 * timeElapsedInSeconds.toDouble() / runLengthInSeconds)

            eventQueue.add(AudioEvent(timeElapsedInSeconds, audioFileName, NonLoopingAudio(audioFile)))
            timeElapsedInSeconds += testConfigs.timeBetweenUpdatesInSeconds
        }
        return eventQueue
    }
}