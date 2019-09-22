package com.molasys.hermes.steps

import com.molasys.hermes.TestConfigs
import com.molasys.hermes.jog.VirtualJog

fun calculateMonsterSteps(virtualMonsterJog: VirtualJog, testConfigs: TestConfigs, numberOfStepsInSession: Float): Float {
    val timeElapsedInSeconds = virtualMonsterJog.timeElapsedInSeconds()
    val numberOfCompletedSteps = virtualMonsterJog.numberOfCompletedSteps()
    val rampUpTime = testConfigs.rampUpTime
    val stepsPerSecond = testConfigs.stepsPerSecond
    val isRampUp = timeElapsedInSeconds <= rampUpTime

    var monsterSteps = numberOfCompletedSteps + when(isRampUp) {
        true -> (stepsPerSecond * timeElapsedInSeconds) / rampUpTime
        false -> stepsPerSecond
    }
    val monsterStepsBehind = numberOfStepsInSession - monsterSteps

    if(monsterStepsBehind < testConfigs.critical && !isRampUp) {
        monsterSteps -= testConfigs.criticalDistanceRubberBanding
    }
    if(numberOfStepsInSession - monsterSteps > testConfigs.maxDistance) {
        monsterSteps = numberOfStepsInSession - testConfigs.maxDistance
    }
    return monsterSteps
}