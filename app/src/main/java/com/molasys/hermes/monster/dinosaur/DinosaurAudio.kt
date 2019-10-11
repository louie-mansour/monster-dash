package com.molasys.hermes.monster.dinosaur

import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.NonLoopingAudio

data class DinosaurAudio(
    val attack: NonLoopingAudio,
    val intimidate: NonLoopingAudio,
    val footsteps: LoopingAudio,
    val sprintingFootsteps: LoopingAudio,
    val vocalizations: LoopingAudio
)