package com.molasys.hermes.audio

interface ChangeableVolume {
    fun changeVolumeByDistance(distanceBetween: Float, dangerThreshold: Int)
}