package com.molasys.hermes.monster.dinosaur

import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.NonLoopingAudio
import com.molasys.hermes.audio.increasingVolumeWithProximity
import com.molasys.hermes.jog.VirtualJog
import com.molasys.hermes.monster.Monster

class Dinosaur(
    configs: DinosaurConfigs,
    private val dinosaurAudio: DinosaurAudio
) : Monster {
    val danger = configs.danger
    private val critical = configs.critical
    private val roarTimeBetween = configs.roarTimeBetween
    private val maxDistance = configs.maxDistance
    private val criticalDistanceRubberBanding = configs.criticalDistanceRubberBanding
    private var stepsPerSecond = configs.stepsPerSecond
    private val virtualJog: VirtualJog = VirtualJog()
    private val rampUpTime = 5
    private var isChasing = false
    private var remainingRampUpTime = rampUpTime

    override fun startChasing(timeElapsedInSeconds: Int) {
        dinosaurAudio.intimidate.play(timeElapsedInSeconds)
        dinosaurAudio.footsteps.play()
        dinosaurAudio.vocalizations.play()
        isChasing = true
    }

    override fun stopChasing() {
        dinosaurAudio.footsteps.stop()
        dinosaurAudio.vocalizations.stop()
        isChasing = false
    }

    override fun isChasing(): Boolean {
        return isChasing
    }

    override fun updateDistance(numberOfSteps: Float) {
        virtualJog.addDistance(numberOfSteps)
        if(isRampingUpSpeed()) {
            remainingRampUpTime -= 1
        }
    }

    override fun isRampingUpSpeed(): Boolean {
        return remainingRampUpTime > 0
    }

    override fun rampUpSpeedModifier(): Float {
        return (rampUpTime - remainingRampUpTime) / rampUpTime.toFloat()
    }

    override fun isInIntimidationRange(distance: Float): Boolean {
        return distance <= critical
    }

    override fun intimidate(timeElapsedInSeconds: Int) {
        if (dinosaurAudio.intimidate.lastPlayed + roarTimeBetween < timeElapsedInSeconds) {
            dinosaurAudio.intimidate.play(timeElapsedInSeconds)
        }
    }

    override fun isInAttackRange(distance: Float): Boolean {
        return distance <= 0f
    }

    override fun attack(timeElapsedInSeconds: Int) {
        dinosaurAudio.attack.play(timeElapsedInSeconds)
        remainingRampUpTime = rampUpTime
    }

    override fun distanceCovered(): Float {
        return virtualJog.distanceCovered()
    }

    override fun getAudio(): List<LoopingAudio> {
        return listOf(dinosaurAudio.footsteps, dinosaurAudio.vocalizations)
    }

    override fun startSpecialEffect() {
        dinosaurAudio.footsteps.stop()
        dinosaurAudio.sprintingFootsteps.play()
        dinosaurAudio.sprintingFootsteps.setVolume(1f)
        stepsPerSecond += 0.5f
    }

    override fun stopSpecialEffect() {
        dinosaurAudio.sprintingFootsteps.stop()
        dinosaurAudio.footsteps.play()
        dinosaurAudio.footsteps.setVolume(1f)
        stepsPerSecond -= 0.5f
    }

    override fun changeVolumeByDistance(monsterStepsBehind: Float) {

    }

    override fun distanceToCover(monsterStepsBehind: Float): Float {
        val isRampingUpSpeed = isRampingUpSpeed()
        val stepsPerSecond = stepsPerSecond
        val maxDistance = maxDistance

        var monsterDistance = distanceCovered() + when (isRampingUpSpeed) {
            true -> stepsPerSecond * rampUpSpeedModifier()
            false -> stepsPerSecond
        }

        if (isInIntimidationRange(monsterStepsBehind) && !isRampingUpSpeed) {
            monsterDistance -= criticalDistanceRubberBanding
        }
        if (monsterStepsBehind > maxDistance) {
            monsterDistance += maxDistance - monsterDistance
        }
        if (monsterStepsBehind < 0) {
            monsterDistance -= monsterStepsBehind
        }
        return monsterDistance
    }
}