package com.molasys.hermes.audio

import com.molasys.hermes.TestConfigs

class MonsterAudioService(
    private val monsterFootsteps: LoopingMonsterAudioPlayer,
    private val monsterVocalizations: LoopingMonsterAudioPlayer,
    private val backgroundNoise: LoopingMonsterAudioPlayer,
    private val monsterCriticalNoise: NonLoopingMonsterAudioPlayer,
    private val monsterBiteNoise: NonLoopingMonsterAudioPlayer,
    private val testConfigs: TestConfigs) {

    fun playAudio() {
        monsterFootsteps.play()
        monsterVocalizations.play()
        backgroundNoise.play()
        monsterCriticalNoise.play(0)
    }

    fun updateAudio(monsterStepsBehind: Float, timeElapsedInSeconds: Int) {
        val monsterVolume = increasingVolumeWithProximity(monsterStepsBehind, testConfigs.danger)
        monsterFootsteps.setVolume(monsterVolume)
        monsterVocalizations.setVolume(monsterVolume)

        val backgroundVolume = decreasingVolumeWithProximity(monsterStepsBehind, testConfigs.danger)
        backgroundNoise.setVolume(backgroundVolume)
        if(monsterStepsBehind == 0f) {
            monsterBiteNoise.play(timeElapsedInSeconds)
            return
        }

        val timeSinceLastRoar = timeElapsedInSeconds - monsterCriticalNoise.lastPlayed
        if(monsterStepsBehind <= testConfigs.critical && (timeSinceLastRoar >= testConfigs.roarTimeBetween || monsterCriticalNoise.lastPlayed == 0)) {
            monsterCriticalNoise.play(timeElapsedInSeconds)
        }
    }
}