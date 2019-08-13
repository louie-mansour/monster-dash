package com.example.hermes.steps

import com.example.hermes.TestConfigs
import com.example.hermes.jog.VirtualJog

fun calculateMonsterSteps(virtualMonsterJog: VirtualJog, testConfigs: TestConfigs, numberOfStepsInSession: Float): Float {
    var monsterSteps = virtualMonsterJog.numberOfCompletedSteps() + testConfigs.stepsPerSecond
    val monsterStepsBehind = numberOfStepsInSession - monsterSteps

    if(numberOfStepsInSession - monsterSteps > testConfigs.maxDistance) {
        monsterSteps = numberOfStepsInSession - testConfigs.maxDistance
    } else if(monsterStepsBehind < testConfigs.critical) {
        monsterSteps -= testConfigs.criticalDistanceRubberBanding
    }
    return numberOfStepsInSession - monsterSteps
}