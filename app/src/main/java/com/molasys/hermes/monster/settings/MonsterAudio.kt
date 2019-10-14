package com.molasys.hermes.monster.settings

import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.NonLoopingAudio

data class MonsterAudio(
    val attack: NonLoopingAudio,
    val intimidate: NonLoopingAudio,
    val footsteps: LoopingAudio,
    val sprintingFootsteps: LoopingAudio,
    val vocalizations: LoopingAudio
)