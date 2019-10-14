package com.molasys.hermes.events

class MonsterEvent(override val timeToPlay: Int, override val action: String) : Event(timeToPlay, action)

val START_SPECIAL_EFFECT = "start_special_effect"
val STOP_SPECIAL_EFFECT = "stop_special_effect"