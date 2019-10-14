package com.molasys.hermes.monster

import com.molasys.hermes.audio.ChangeableVolume
import com.molasys.hermes.jog.CoversDistance

interface Monster : ChangeableVolume, CoversDistance {
    fun startChasing(timeElapsedInSeconds: Int)

    fun stopChasing()

    fun isChasing(): Boolean

    fun coverDistance(numberOfSteps: Float)

    fun isRampingUpSpeed(): Boolean

    fun intimidate(timeElapsedInSeconds: Int)

    fun attack(timeElapsedInSeconds: Int)

    fun startSpecialEffect()

    fun stopSpecialEffect()

    fun distanceToCover(): Float
}