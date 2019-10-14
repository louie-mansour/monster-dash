package com.molasys.hermes.monster.settings

import android.content.Context
import com.molasys.hermes.TestConfigs
import com.molasys.hermes.monster.settings.MonsterAudio
import com.molasys.hermes.monster.settings.MonsterConfigs

interface MonsterSettings {
    fun buildAudio(applicationContext: Context) : MonsterAudio

    fun buildConfigs(testConfigs: TestConfigs) : MonsterConfigs
}