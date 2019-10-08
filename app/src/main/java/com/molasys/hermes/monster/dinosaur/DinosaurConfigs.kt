package com.molasys.hermes.monster.dinosaur

data class DinosaurConfigs(
    val danger: Int,
    val critical: Int,
    val roarTimeBetween: Int,
    val maxDistance: Float,
    val criticalDistanceRubberBanding: Float,
    val stepsPerSecond: Float,
    val runTimeInSeconds: Int
)