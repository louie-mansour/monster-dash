package com.molasys.hermes.steps

import com.molasys.hermes.TestConfigs
import com.molasys.hermes.jog.VirtualJog

class MonsterStepsService(testConfigs: TestConfigs) {
    private val stepsPerSecond = testConfigs.stepsPerSecond
    private val criticalDistance = testConfigs.critical
    private val criticalDistanceRubberBanding = testConfigs.criticalDistanceRubberBanding
    private val maxDistance = testConfigs.maxDistance
    private val rampUpDuration = testConfigs.rampUpDuration
    private var timeOfLastRampUp = 0f

    fun calculateMonsterSteps(monsterJog: VirtualJog, userSteps: Float): Float {
        val timeElapsedInSeconds = monsterJog.timeElapsedInSeconds()
        val isRampingUp = timeElapsedInSeconds <= timeOfLastRampUp + rampUpDuration

        var monsterSteps = monsterJog.numberOfCompletedSteps() + when (isRampingUp) {
            true -> stepsPerSecond * ((timeElapsedInSeconds - timeOfLastRampUp) / rampUpDuration)
            false -> stepsPerSecond
        }
        val monsterStepsBehind = userSteps - monsterSteps

        if (monsterStepsBehind < criticalDistance && !isRampingUp) {
            monsterSteps -= criticalDistanceRubberBanding }
        if (userSteps - monsterSteps > maxDistance) {
            monsterSteps = userSteps - maxDistance
        }
        return monsterSteps
    }

    fun startNewRampUp(currentNumberOfSecondsElapsed: Float) {
        timeOfLastRampUp = currentNumberOfSecondsElapsed
    }
}