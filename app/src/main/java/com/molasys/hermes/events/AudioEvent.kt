package com.molasys.hermes.events

import com.molasys.hermes.audio.NonLoopingAudio

class AudioEvent(override val timeToPlay: Int, override val action: String, val audio: NonLoopingAudio) : Event(timeToPlay, action) {
    fun play() {
        audio.play(timeToPlay)
    }
}