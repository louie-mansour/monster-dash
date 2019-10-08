package com.molasys.hermes.audio

fun increasingVolumeWithProximity(monsterStepsBehind: Float, dangerThreshold: Int): Float {
    if(monsterStepsBehind > dangerThreshold) {
        return 0f
    }
    val rawMonsterVolumeLevel = dangerThreshold - monsterStepsBehind
    return volumeLevelTranspose(rawMonsterVolumeLevel, dangerThreshold)
}

fun decreasingVolumeWithProximity(monsterStepsBehind: Float, dangerThreshold: Int): Float {
    if(monsterStepsBehind > dangerThreshold) {
        return 1f
    }
    return volumeLevelTranspose(monsterStepsBehind, dangerThreshold)
}

private fun volumeLevelTranspose(linearVolumeLevel: Float, dangerThreshold: Int): Float {
    val zeroVolumeSteps = dangerThreshold.toDouble()
    return 1 - (Math.log(zeroVolumeSteps - linearVolumeLevel) / Math.log(zeroVolumeSteps)).toFloat()
}