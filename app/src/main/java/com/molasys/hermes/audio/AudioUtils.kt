package com.molasys.hermes.audio

import com.molasys.hermes.monster.Monster

fun updateLoopingAudio(monsterStepsBehind: Float, monster: Monster, background: ChangeableVolume) {
    val dangerThreshold = monster.danger

    val monsterVolume = increasingVolumeWithProximity(monsterStepsBehind, dangerThreshold)
    monster.getAudio().forEach { audio -> audio.setVolume(monsterVolume)}

    val backgroundVolume = decreasingVolumeWithProximity(monsterStepsBehind, dangerThreshold)
    background.getAudio().forEach { audio -> audio.setVolume(backgroundVolume) }
}