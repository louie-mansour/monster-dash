package com.example.hermes.audio

import android.content.Context
import com.example.hermes.R
import com.example.hermes.TestConfigs

class MonsterAudioService(applicationContext: Context, private val testConfigs: TestConfigs) {
    var monsterFootsteps: LoopingMonsterAudioPlayer =
        LoopingMonsterAudioPlayer(applicationContext, R.raw.dinosaur_steps_amp)
    var monsterVocalizations: LoopingMonsterAudioPlayer =
        LoopingMonsterAudioPlayer(applicationContext, R.raw.dinosaur_vocalization)
    var backgroundNoise: LoopingMonsterAudioPlayer =
        LoopingMonsterAudioPlayer(
            applicationContext,
            R.raw.dinosaur_background_quieter
        )
    var monsterCriticalNoise: NonLoopingMonsterAudioPlayer =
        NonLoopingMonsterAudioPlayer(applicationContext, R.raw.dinosaur_big_roar)

    fun playAudio() {
        monsterFootsteps.play()
        monsterVocalizations.play()
        backgroundNoise.play()
        monsterCriticalNoise.play(0)
    }

    fun updateAudio(monsterStepsBehind: Float, timeElapsedInSeconds: Int) {
        val monsterVolume = monsterVolumeLevel(monsterStepsBehind)
        monsterFootsteps.setVolume(monsterVolume)
        monsterVocalizations.setVolume(monsterVolume)

        val backgroundVolume = backgroundVolumeLevel(monsterStepsBehind)
        backgroundNoise.setVolume(backgroundVolume)

        val timeSinceLastRoar = timeElapsedInSeconds - monsterCriticalNoise.lastPlayed
        if(monsterStepsBehind <= testConfigs.critical && (timeSinceLastRoar >= testConfigs.roarTimeBetween || monsterCriticalNoise.lastPlayed == 0)) {
            monsterCriticalNoise.play(timeElapsedInSeconds)
        }
    }

    private fun monsterVolumeLevel(monsterStepsBehind: Float): Float {
        if(monsterStepsBehind > testConfigs.danger) {
            return 0f
        }
        val rawMonsterVolumeLevel = testConfigs.danger - monsterStepsBehind
        return volumeLevelTranspose(rawMonsterVolumeLevel, testConfigs.danger)
    }

    private fun backgroundVolumeLevel(monsterStepsBehind: Float): Float {
        if(monsterStepsBehind > testConfigs.danger) {
            return 1f
        }
        return volumeLevelTranspose(monsterStepsBehind, testConfigs.danger)
    }

    private fun volumeLevelTranspose(linearVolumeLevel: Float, stepsConfig: Int): Float {
        val zeroVolumeSteps = stepsConfig.toDouble()
        return 1 - (Math.log(zeroVolumeSteps - linearVolumeLevel) / Math.log(zeroVolumeSteps)).toFloat()
    }
}