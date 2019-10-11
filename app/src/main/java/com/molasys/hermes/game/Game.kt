package com.molasys.hermes.game

import com.molasys.hermes.activities.DisplayData
import com.molasys.hermes.audio.updateLoopingAudio
import com.molasys.hermes.events.EventQueue
import com.molasys.hermes.monster.Monster
import com.molasys.hermes.monster.distanceBetween
import com.molasys.hermes.surroundings.Background
import com.molasys.hermes.users.User

class Game(
    private val user: User,
    private val monster: Monster,
    private val eventQueue: EventQueue,
    private val background: Background
) {
    init {
        background.setScene()
    }

    fun isStarted(): Boolean {
        return user.isRunning()
    }

    fun tick(timeElapsedInSeconds: Int) {
        if(!monster.isChasing()) {
            monster.startChasing(timeElapsedInSeconds)
        }

        while(eventQueue.isEventsToPlay(timeElapsedInSeconds)) {
            eventQueue.playEvent(eventQueue.poll(), monster)
        }
        user.updateDistanceUsingSensor()
        monster.updateDistance(distanceBetween(monster, user))

        val distanceBetweenMonsterAndUser = distanceBetween(monster, user)
        updateLoopingAudio(distanceBetweenMonsterAndUser, monster, background)

        if(monster.isInIntimidationRange(distanceBetweenMonsterAndUser)) {
            monster.intimidate(timeElapsedInSeconds)
        }
        if(monster.isInAttackRange(distanceBetweenMonsterAndUser)) {
            monster.attack(timeElapsedInSeconds)
        }
    }

    fun isFinished(): Boolean {
        return eventQueue.isEmpty()
    }

    fun stop() {
        return monster.stopChasing()
    }

    fun getDisplayData(): DisplayData {
        return DisplayData(
            user.distanceCovered(),
            distanceBetween(monster, user),
            eventQueue.size()
        )
    }
}