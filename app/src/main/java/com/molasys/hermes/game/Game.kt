package com.molasys.hermes.game

import com.molasys.hermes.activities.DisplayData
import com.molasys.hermes.audio.updateVolumesByProximity
import com.molasys.hermes.events.EventQueue
import com.molasys.hermes.jog.CoversDistance
import com.molasys.hermes.monster.Monster
import com.molasys.hermes.surroundings.Background
import com.molasys.hermes.users.User

class Game(
    private val user: User,
    private val monster: Monster,
    private val eventQueue: EventQueue,
    private val background: Background,
    gameConfigs: GameConfigs
    ) {
    private val dangerDistance = gameConfigs.dangerDistance
    private val criticalDistance = gameConfigs.criticalDistance
    private val criticalDistanceRubberBanding = gameConfigs.criticalDistanceRubberBanding
    private val maxDistance = gameConfigs.maxDistance

    init {
        background.setScene()
    }

    fun isStarted(): Boolean { return user.isRunning() }

    fun isFinished(): Boolean { return eventQueue.isEmpty() }

    fun stop() { return monster.stopChasing() }

    fun tick(timeElapsedInSeconds: Int) {
        if(!monster.isChasing()) {
            monster.startChasing(timeElapsedInSeconds)
        }

        while(eventQueue.isEventsToPlay(timeElapsedInSeconds)) {
            eventQueue.playEvent(eventQueue.poll(), monster)
        }
        user.updateDistanceUsingSensor()
        monster.coverDistance(monsterMovementWithRubberBanding(monster, user))

        val distanceBetweenMonsterAndUser = distanceBetween(monster, user)
        updateVolumesByProximity(listOf(monster, background), distanceBetweenMonsterAndUser, dangerDistance)

        if(isInIntimidationRange(distanceBetweenMonsterAndUser)) {
            monster.intimidate(timeElapsedInSeconds)
        }
        if(isInAttackRange(distanceBetweenMonsterAndUser)) {
            monster.attack(timeElapsedInSeconds)
        }
    }

    fun getDisplayData(): DisplayData {
        return DisplayData(
            user.distanceCovered(),
            distanceBetween(monster, user),
            eventQueue.size()
        )
    }

    private fun monsterMovementWithRubberBanding(monster: Monster, user: User): Float {
        var distanceToCover = monster.distanceToCover()
        val distanceBetween = distanceBetween(monster, user)

        if (isInIntimidationRange(distanceBetween)) {
            distanceToCover -= criticalDistanceRubberBanding
        }
        if (distanceBetween > maxDistance) {
            distanceToCover = user.distanceCovered() - maxDistance
        }
        if (distanceBetween < 0) {
            distanceToCover = user.distanceCovered()
        }
        return distanceToCover
    }

    fun distanceBetween(monster: CoversDistance, user: CoversDistance): Float {
        return user.distanceCovered() - monster.distanceCovered()
    }

    private fun isInIntimidationRange(distanceBetween: Float): Boolean {
        return distanceBetween <= criticalDistance
    }

    private fun isInAttackRange(distanceBetween: Float): Boolean {
        return distanceBetween <= 0
    }
}