package com.example.hermes

fun monsterStepsFactory(stepsPerSecond: Float, runLengthInSeconds: Int): MutableList<Float> {
    val monsterSteps = mutableListOf<Float>()
    for(step in 0..60 * runLengthInSeconds) {
        monsterSteps.add(step.toFloat() * stepsPerSecond)
    }
    monsterSteps.add(0f)
    return monsterSteps
}