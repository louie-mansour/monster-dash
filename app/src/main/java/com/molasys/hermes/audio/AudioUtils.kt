package com.molasys.hermes.audio

import com.molasys.hermes.monster.dinosaur.Dinosaur

fun updateLoopingAudio(monsterStepsBehind: Float, dinosaur: Dinosaur, background: ChangeableVolume) {
    val dangerThreshold = dinosaur.configs.danger

    val monsterVolume = increasingVolumeWithProximity(monsterStepsBehind, dangerThreshold)
    dinosaur.getAudio().forEach { audio -> audio.setVolume(monsterVolume)}

    val backgroundVolume = decreasingVolumeWithProximity(monsterStepsBehind, dangerThreshold)
    background.getAudio().forEach { audio -> audio.setVolume(backgroundVolume) }
}