package com.molasys.hermes.audio

fun updateVolumesByProximity(listOfChangeableVolumes: List<ChangeableVolume>, distanceBetween: Float, dangerThreshold: Int) {
    listOfChangeableVolumes.forEach{
            changeableVolume -> changeableVolume.changeVolumeByDistance(distanceBetween, dangerThreshold)
    }
}

fun increasingVolumeWithProximity(distanceBetween: Float, dangerThreshold: Int): Float {
    if(distanceBetween > dangerThreshold) {
        return 0f
    }
    val rawMonsterVolumeLevel = dangerThreshold - distanceBetween
    return volumeLevelTranspose(rawMonsterVolumeLevel, dangerThreshold)
}

fun decreasingVolumeWithProximity(distanceBetween: Float, dangerThreshold: Int): Float {
    if(distanceBetween > dangerThreshold) {
        return 1f
    }
    return volumeLevelTranspose(distanceBetween, dangerThreshold)
}

private fun volumeLevelTranspose(linearVolumeLevel: Float, dangerThreshold: Int): Float {
    val zeroVolumeSteps = dangerThreshold.toDouble()
    return 1 - (Math.log(zeroVolumeSteps - linearVolumeLevel) / Math.log(zeroVolumeSteps)).toFloat()
}