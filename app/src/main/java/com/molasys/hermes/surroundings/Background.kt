package com.molasys.hermes.surroundings

import com.molasys.hermes.audio.ChangeableVolume
import com.molasys.hermes.audio.LoopingAudio

class Background(private val backgroundNoise: LoopingAudio) : ChangeableVolume {

    override fun updateVolume(): List<LoopingAudio> {
        return listOf(backgroundNoise)
    }

    fun setScene() {
        backgroundNoise.setVolume(1f)
        backgroundNoise.play()
    }
}