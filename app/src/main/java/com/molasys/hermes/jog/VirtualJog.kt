package com.molasys.hermes.jog

class VirtualJog : CoversDistance {
    private var distancesCovered: MutableList<Float> = mutableListOf(0f)

    fun addDistance(distanceCovered: Float): VirtualJog {
        distancesCovered.add(distanceCovered)
        return this
    }

    override fun distanceCovered(): Float {
        return distancesCovered[distancesCovered.size - 1]
    }
}