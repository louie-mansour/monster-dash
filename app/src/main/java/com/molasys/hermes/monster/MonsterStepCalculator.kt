package com.molasys.hermes.monster

import com.molasys.hermes.monster.dinosaur.Dinosaur
import com.molasys.hermes.users.User

fun calculateMonsterDistance(dinosaur: Dinosaur, user: User): Float {
    val isRampingUpSpeed = dinosaur.isRampingUpSpeed()
    val stepsPerSecond = dinosaur.configs.stepsPerSecond
    val maxDistance = dinosaur.configs.maxDistance
    val userDistance = user.distanceCovered()

    var monsterDistance = dinosaur.distanceCovered() + when (isRampingUpSpeed) {
        true -> stepsPerSecond * dinosaur.rampUpSpeedModifier()
        false -> stepsPerSecond
    }

    val monsterStepsBehind = userDistance - monsterDistance
    if (dinosaur.isInIntimidationRange(monsterStepsBehind) && !isRampingUpSpeed) {
        monsterDistance -= dinosaur.configs.criticalDistanceRubberBanding
    }
    if (userDistance - monsterDistance > maxDistance) {
        monsterDistance = userDistance - maxDistance
    }
    if (monsterDistance >= userDistance) {
        monsterDistance = userDistance
    }
    return monsterDistance
}