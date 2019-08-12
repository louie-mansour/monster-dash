package com.example.hermes

import android.content.Context
import android.media.MediaPlayer

class MonsterAudio(applicationContext: Context, val monsterAudioConfig: MonsterAudioConfig) {
    var dinoSteps: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.dinosaur_steps_amp)
    var dinoVocalization: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.dinosaur_vocalization)
    var dinoBackground: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.dinosaur_background_quieter)
    var dinoBigRoar: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.dinosaur_big_roar)
    var bigRoarLastPlayed: Int? = null

    init {
        reset()
    }

    fun setMonsterAudio(monsterStepsBehind: Float) {
        val dinoStepVolume = volumeLevelTranspose(monsterStepVolumeLevel(monsterStepsBehind), monsterAudioConfig.danger.toFloat()).toFloat()
        dinoSteps.setVolume(dinoStepVolume, dinoStepVolume)

        val dinoVocalizationVolume = volumeLevelTranspose(monsterVocalizationVolumeLevel(monsterStepsBehind), monsterAudioConfig.danger.toFloat()).toFloat()
        dinoVocalization.setVolume(dinoVocalizationVolume, dinoVocalizationVolume)

        val dinoBackgroundVolume = volumeLevelTranspose(monsterBackgroundVolumeLevel(monsterStepsBehind), monsterAudioConfig.danger.toFloat()).toFloat()
        dinoBackground.setVolume(dinoBackgroundVolume, dinoBackgroundVolume)

        playBigRoar(monsterStepsBehind)
    }

    fun reset() {
        dinoSteps.isLooping = true
        dinoSteps.setVolume(0f,0f)
        dinoSteps.start()

        dinoVocalization.isLooping = true
        dinoVocalization.setVolume(0f,0f)
        dinoVocalization.start()

        dinoBackground.isLooping = true
        dinoBackground.setVolume(0f,0f)
        dinoBackground.start()

        dinoBigRoar.isLooping = false
        dinoBigRoar.setVolume(1f,1f)
        dinoBigRoar.start()

        bigRoarLastPlayed = null
    }

    private fun monsterStepVolumeLevel(monsterStepsBehind: Float): Double {
        if(monsterStepsBehind > monsterAudioConfig.danger) {
            return 0.0
        }
        return monsterAudioConfig.danger - monsterStepsBehind.toDouble()
    }

    private fun monsterVocalizationVolumeLevel(monsterStepsBehind: Float): Double {
        if(monsterStepsBehind > monsterAudioConfig.danger) {
            return 0.0
        }
        return monsterAudioConfig.danger - monsterStepsBehind.toDouble()
    }

    private fun monsterBackgroundVolumeLevel(monsterStepsBehind: Float): Double {
        if(monsterStepsBehind > monsterAudioConfig.danger) {
            return monsterAudioConfig.danger.toDouble()
        }
        return monsterStepsBehind.toDouble()
    }

    private fun playBigRoar(monsterStepsBehind: Float) {
        val mBigRoarLastPlayed: Int? = bigRoarLastPlayed
        if(monsterStepsBehind <= monsterAudioConfig.critical && (mBigRoarLastPlayed == null || mBigRoarLastPlayed >= monsterAudioConfig.roarTimeBetween)) {
            dinoBigRoar.start()
        }
    }

    private fun volumeLevelTranspose(linearVolumeLevel: Double, stepsConfig: Float): Double {
        return 1 - (Math.log(stepsConfig - linearVolumeLevel) / Math.log(stepsConfig.toDouble()))
    }
}