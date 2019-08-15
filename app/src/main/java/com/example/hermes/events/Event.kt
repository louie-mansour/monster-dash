package com.example.hermes.events

import android.media.MediaPlayer

class Event(val timeElapsedInSeconds: Int, val audio: MediaPlayer, val name: String): Comparable<Event> {
    override operator fun compareTo(other: Event): Int {
        if (this.timeElapsedInSeconds > other.timeElapsedInSeconds) return 1
        if (this.timeElapsedInSeconds < other.timeElapsedInSeconds) return -1
        return 0
    }
}