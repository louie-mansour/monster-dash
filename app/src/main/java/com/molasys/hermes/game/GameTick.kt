package com.molasys.hermes.game

import android.os.Handler
import com.molasys.hermes.activities.DisplayData
import com.molasys.hermes.audio.updateLoopingAudio
import com.molasys.hermes.events.Event
import com.molasys.hermes.monster.calculateMonsterDistance
import com.molasys.hermes.monster.dinosaur.Dinosaur
import com.molasys.hermes.surroundings.Background
import com.molasys.hermes.users.User
import java.util.*

class GameTick(
    private val tickHandler: Handler,
    private val user: User,
    private val dinosaur: Dinosaur,
    private val eventQueue: Queue<Event>,
    private val background: Background,
    private val updateDisplay: (DisplayData) -> Unit
) : Runnable {
    private var timeElapsedInSeconds = 1
    override fun run() {
        if(!user.isRunning()) {
            tickHandler.postDelayed(this, 1000)
            return
        }

        if(eventQueue.isEmpty()) {
            tickHandler.removeCallbacks(this);
            dinosaur.stopChasing()
            return
        }

        if(!dinosaur.isChasing()) {
            dinosaur.startChasing(timeElapsedInSeconds)
        }

        if(timeElapsedInSeconds >= eventQueue.peek().timeElapsedInSeconds) {
            eventQueue.poll().audio.start()
        }
        user.updateDistanceUsingSensor()
        dinosaur.addDistance(calculateMonsterDistance(dinosaur, user))

        // set monster audio to new levels
        val dinosaurStepsBehind = user.distanceCovered() - dinosaur.distanceCovered()

        updateLoopingAudio(dinosaurStepsBehind, dinosaur, background)

        if(dinosaur.isInIntimidationRange(dinosaurStepsBehind)) {
            dinosaur.intimidate(timeElapsedInSeconds)
        }
        if(dinosaur.isInAttackRange(dinosaurStepsBehind)) {
            dinosaur.attack(timeElapsedInSeconds)
        }

        tickHandler.postDelayed(this, 1000)

        updateDisplay(DisplayData(
                user.distanceCovered(),
                dinosaurStepsBehind,
                timeElapsedInSeconds,
                dinosaur.configs.runTimeInSeconds,
                eventQueue.size
        ))
        timeElapsedInSeconds += 1
    }
}