package com.molasys.hermes.events

import com.molasys.hermes.monster.Monster
import java.util.*

class EventQueue {
    private var queue = PriorityQueue<Event>()

    fun isEventsToPlay(timeElapsedInSeconds: Int): Boolean {
        return timeElapsedInSeconds >= queue.peek().timeToPlay
    }

    fun poll(): Event {
        return queue.poll()
    }

    fun playEvent(event: Event, monster: Monster) {
        when (event) {
            is AudioEvent -> event.play()
            is MonsterEvent -> {
                when {
                    event.action == "start_special_effect" -> monster.startSpecialEffect()
                    event.action == "stop_special_effect" -> monster.stopSpecialEffect()
                }
            }
        }
    }

    fun add(event: Event): Boolean {
        return queue.add(event)
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    fun size(): Int {
        return queue.size
    }
}