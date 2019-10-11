package com.molasys.hermes.activities

data class DisplayData(
    var numberOfCompletedSteps: Float,
    var monsterStepsBehind: Float,
    var eventQueueSize: Int,
    var timeElapsedInSeconds: Int = 1
)