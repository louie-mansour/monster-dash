package com.molasys.hermes.monster.ghost

import com.molasys.hermes.audio.increasingVolumeWithProximity
import com.molasys.hermes.jog.VirtualJog
import com.molasys.hermes.monster.Monster
import com.molasys.hermes.monster.settings.MonsterAudio
import com.molasys.hermes.monster.settings.MonsterConfigs

class Ghost(
    configs: MonsterConfigs,
    private val audio: MonsterAudio
) : Monster {
    private val sprintingModifier = 1.1f
    private val virtualJog: VirtualJog = VirtualJog()
    private val rampUpTime = 5
    private var remainingRampUpTime = rampUpTime
    private val roarTimeBetween = configs.roarTimeBetween

    private var isChasing = false
    private var stepsPerSecond = configs.stepsPerSecond

    override fun startChasing(timeElapsedInSeconds: Int) {
        audio.intimidate.play(timeElapsedInSeconds)
        audio.footsteps.play()
        audio.vocalizations.play()
        isChasing = true
    }

    override fun stopChasing() {
        audio.footsteps.stop()
        audio.vocalizations.stop()
        isChasing = false
    }

    override fun isChasing(): Boolean {
        return isChasing
    }

    override fun coverDistance(numberOfSteps: Float) {
        virtualJog.addDistance(numberOfSteps)
        if(isRampingUpSpeed()) {
            remainingRampUpTime -= 1
        }
    }

    override fun isRampingUpSpeed(): Boolean {
        return remainingRampUpTime > 0
    }

    override fun intimidate(timeElapsedInSeconds: Int) {
        if (audio.intimidate.lastPlayed + roarTimeBetween < timeElapsedInSeconds) {
            audio.intimidate.play(timeElapsedInSeconds)
        }
    }

    override fun attack(timeElapsedInSeconds: Int) {
        audio.attack.play(timeElapsedInSeconds)
        remainingRampUpTime = rampUpTime
    }

    override fun distanceCovered(): Float {
        return virtualJog.distanceCovered()
    }

    override fun startSpecialEffect() {
        audio.footsteps.stop()
        audio.sprintingFootsteps.play()
        audio.sprintingFootsteps.setVolume(1f)
        stepsPerSecond *= sprintingModifier
    }

    override fun stopSpecialEffect() {
        audio.sprintingFootsteps.stop()
        audio.footsteps.play()
        audio.footsteps.setVolume(1f)
        stepsPerSecond /= sprintingModifier
    }

    override fun changeVolumeByDistance(distanceBetween: Float, dangerThreshold: Int) {
        val monsterVolume = increasingVolumeWithProximity(distanceBetween, dangerThreshold)
        listOf(audio.footsteps, audio.sprintingFootsteps, audio.vocalizations)
            .forEach { audio -> audio.setVolume(monsterVolume)}
    }

    override fun distanceToCover(): Float {
        val stepsPerSecond = stepsPerSecond

        return distanceCovered() + when (isRampingUpSpeed()) {
            true -> stepsPerSecond * rampUpSpeedModifier()
            false -> stepsPerSecond
        }
    }

    private fun rampUpSpeedModifier(): Float {
        return (rampUpTime - remainingRampUpTime) / rampUpTime.toFloat()
    }
}