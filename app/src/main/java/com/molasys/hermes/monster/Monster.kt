package com.molasys.hermes.monster

import com.molasys.hermes.audio.ChangeableVolume

interface Monster : ChangeableVolume {
    fun startChasing(timeElapsedInSeconds: Int)

    fun stopChasing()

    fun isChasing(): Boolean

    fun updateDistance(numberOfSteps: Float)

    fun isRampingUpSpeed(): Boolean

    fun rampUpSpeedModifier(): Float

    fun isInIntimidationRange(distance: Float): Boolean

    fun intimidate(timeElapsedInSeconds: Int)

    fun isInAttackRange(distance: Float): Boolean

    fun attack(timeElapsedInSeconds: Int)

    fun distanceCovered(): Float

    fun startSpecialEffect()

    fun stopSpecialEffect()

    fun distanceToCover(monsterStepsBehind: Float): Float
}