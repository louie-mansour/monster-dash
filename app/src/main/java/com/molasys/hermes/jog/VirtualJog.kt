package com.molasys.hermes.jog

class VirtualJog {
    private var allSteps: MutableList<Float> = mutableListOf(0f)

    fun addSteps(numberOfSteps: Float): VirtualJog {
        allSteps.add(numberOfSteps)
        return this
    }

    fun timeElapsedInSeconds(): Int {
        return allSteps.size - 1
    }

    fun numberOfCompletedSteps(): Float {
        return allSteps[allSteps.size - 1]
    }
}