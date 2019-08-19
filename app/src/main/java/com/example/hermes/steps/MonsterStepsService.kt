package com.example.hermes.steps

import com.example.hermes.TestConfigs
import com.example.hermes.jog.VirtualJog

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