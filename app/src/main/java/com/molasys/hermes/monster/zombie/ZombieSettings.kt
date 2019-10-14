package com.molasys.hermes.monster.zombie

import android.content.Context
import android.media.MediaPlayer
import com.molasys.hermes.R
import com.molasys.hermes.TestConfigs
import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.NonLoopingAudio
import com.molasys.hermes.monster.settings.MonsterAudio
import com.molasys.hermes.monster.settings.MonsterConfigs
import com.molasys.hermes.monster.settings.MonsterSettings

class ZombieSettings {
    companion object : MonsterSettings {
        override fun buildAudio(applicationContext: Context): MonsterAudio {
            return MonsterAudio(
                NonLoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_bite)),
                NonLoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_big_roar)),
                LoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_steps_amp)),
                LoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_steps_sprint)),
                LoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_vocalization))
            )
        }

        override fun buildConfigs(testConfigs: TestConfigs): MonsterConfigs {
            return MonsterConfigs(testConfigs.danger, testConfigs.stepsPerSecond)
        }
    }
}