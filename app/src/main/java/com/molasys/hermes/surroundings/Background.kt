package com.molasys.hermes.surroundings

import com.molasys.hermes.audio.ChangeableVolume
import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.decreasingVolumeWithProximity

class Background(private val backgroundNoise: LoopingAudio) : ChangeableVolume {

    override fun changeVolumeByDistance(distanceBetween: Float, dangerThreshold: Int) {
        val monsterVolume = decreasingVolumeWithProximity(distanceBetween, dangerThreshold)
        listOf(backgroundNoise)
            .forEach { audio -> audio.setVolume(monsterVolume)}
    }

    fun setScene() {
        backgroundNoise.setVolume(1f)
        backgroundNoise.play()
    }
}