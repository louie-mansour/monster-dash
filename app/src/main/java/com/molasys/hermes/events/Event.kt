package com.molasys.hermes.events

abstract class Event(open val timeToPlay: Int, open val action: String): Comparable<Event> {
    override operator fun compareTo(other: Event): Int {
        if (this.timeToPlay > other.timeToPlay) return 1
        if (this.timeToPlay < other.timeToPlay) return -1
        return 0
    }
}