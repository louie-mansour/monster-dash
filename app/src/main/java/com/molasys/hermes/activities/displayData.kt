package com.molasys.hermes.activities

data class DisplayData(
    val numberOfCompletedSteps: Float,
    val monsterStepsBehind: Float,
    val timeElapsedInSeconds: Int,
    val runTimeInSeconds: Int,
    val eventQueueSize: Int
)