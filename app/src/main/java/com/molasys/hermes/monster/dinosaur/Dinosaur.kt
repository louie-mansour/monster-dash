package com.molasys.hermes.monster.dinosaur

import com.molasys.hermes.audio.ChangeableVolume
import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.NonLoopingAudio
import com.molasys.hermes.jog.VirtualJog

class Dinosaur(
    val configs: DinosaurConfigs,
    private val attack: NonLoopingAudio,
    private val intimidate: NonLoopingAudio,
    private val footsteps: LoopingAudio,
    private val vocalizations: LoopingAudio
) : ChangeableVolume {
    private val virtualJog: VirtualJog = VirtualJog()
    private var isChasing = false
    private val rampUpTime = 5
    private var remainingRampUpTime = rampUpTime

    fun startChasing(timeElapsedInSeconds: Int) {
        intimidate.play(timeElapsedInSeconds)
        footsteps.play()
        vocalizations.play()
        isChasing = true
    }

    fun stopChasing() {
        footsteps.stop()
        vocalizations.stop()
        isChasing = false
    }

    fun isChasing(): Boolean {
        return isChasing
    }

    fun addDistance(numberOfSteps: Float) {
        virtualJog.addDistance(numberOfSteps)
        if(isRampingUpSpeed()) {
            remainingRampUpTime -= 1
        }
    }

    fun isRampingUpSpeed(): Boolean {
        return remainingRampUpTime > 0
    }

    fun rampUpSpeedModifier(): Float {
        return (rampUpTime - remainingRampUpTime) / rampUpTime.toFloat()
    }

    fun isInIntimidationRange(distance: Float): Boolean {
        return distance <= configs.critical
    }

    fun intimidate(timeElapsedInSeconds: Int) {
        if (intimidate.lastPlayed + configs.roarTimeBetween < timeElapsedInSeconds) {
            intimidate.play(timeElapsedInSeconds)
        }
    }

    fun isInAttackRange(distance: Float): Boolean {
        return distance <= 0f
    }

    fun attack(timeElapsedInSeconds: Int) {
        attack.play(timeElapsedInSeconds)
        remainingRampUpTime = rampUpTime
    }

    fun distanceCovered(): Float {
        return virtualJog.distanceCovered()
    }

    override fun getAudio(): List<LoopingAudio> {
        return listOf(footsteps, vocalizations)
    }
}