package com.example.hermes

import android.content.Context
import android.media.MediaPlayer
import java.util.*

fun eventQueueFactory(context: Context, runConfig: RunConfig): Queue<Event> {
    val eventQueue:Queue<Event> = ArrayDeque()
    val runLengthInSeconds = runConfig.runTimeInMinutes * 60
    var timeElapsedInSeconds = runConfig.timeBetweenUpdatesInSeconds
    while(timeElapsedInSeconds <= runLengthInSeconds) {
        eventQueue.add(Event(timeElapsedInSeconds, MediaPlayer.create(context, findAudioFile(100 * timeElapsedInSeconds.toDouble() / runLengthInSeconds))))
        timeElapsedInSeconds += runConfig.timeBetweenUpdatesInSeconds
    }
    return eventQueue
}

private fun findAudioFile(percentComplete: Double): Int {
    when {
        percentComplete < 5.0 -> return R.raw.one_percent_complete
        percentComplete < 10.0 -> return R.raw.five_percent_complete
        percentComplete < 15.0 -> return R.raw.ten_percent_complete
        percentComplete < 20.0 -> return R.raw.fifteen_percent_complete
        percentComplete < 25.0 -> return R.raw.twenty_percent_complete
        percentComplete < 30.0 -> return R.raw.twenty_five_percent_complete
        percentComplete < 35.0 -> return R.raw.thirty_percent_complete
        percentComplete < 40.0 -> return R.raw.thrity_five_percent_complete
        percentComplete < 45.0 -> return R.raw.forty_percent_complete
        percentComplete < 50.0 -> return R.raw.forty_five_percent_complete
        percentComplete < 55.0 -> return R.raw.fifty_percent_complete
        percentComplete < 60.0 -> return R.raw.fifty_five_percent_complete
        percentComplete < 65.0 -> return R.raw.sixty_percent_complete
        percentComplete < 70.0 -> return R.raw.sixty_five_percent_complete
        percentComplete < 75.0 -> return R.raw.seventy_percent_complete
        percentComplete < 80.0 -> return R.raw.seventy_five_percent_complete
        percentComplete < 85.0 -> return R.raw.eight_percent_complete
        percentComplete < 90.0 -> return R.raw.eighty_five_percent_complete
        percentComplete < 95.0 -> return R.raw.ninety_percent_complete
        percentComplete < 100.0 -> return R.raw.ninety_five_percent_complete
        else -> return R.raw.one_hundred_percent_complete
    }
}