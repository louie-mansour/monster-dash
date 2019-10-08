package com.molasys.hermes.monster

import android.content.Context
import android.media.MediaPlayer
import com.molasys.hermes.R
import com.molasys.hermes.TestConfigs
import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.NonLoopingAudio
import com.molasys.hermes.monster.dinosaur.Dinosaur
import com.molasys.hermes.monster.dinosaur.DinosaurConfigs

const val DINOSAUR: String = "Dinosaur"

class MonsterFactory(private val applicationContext: Context, private val testConfigs: TestConfigs) {
    fun make(monsterType: String): Dinosaur {
        when (monsterType) {
            DINOSAUR -> {
                val dinosaurConfigs = DinosaurConfigs(
                    testConfigs.danger,
                    testConfigs.critical,
                    testConfigs.roarTimeBetween,
                    testConfigs.maxDistance,
                    testConfigs.criticalDistanceRubberBanding,
                    testConfigs.stepsPerSecond,
                    testConfigs.runTimeInSeconds
                )
                val dinosaurAttack = NonLoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_bite))
                val dinosaurIntimidate = NonLoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_big_roar))
                val dinosaurFootsteps = LoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_steps_amp))
                val dinosaurVocalizations = LoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_vocalization))

                return Dinosaur(dinosaurConfigs, dinosaurAttack, dinosaurIntimidate, dinosaurFootsteps, dinosaurVocalizations)
            }
            else -> throw Exception("Unrecognized monster")
        }
    }
}