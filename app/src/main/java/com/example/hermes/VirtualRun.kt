package com.example.hermes

open class VirtualRun {
    protected var allSteps: MutableList<Float> = mutableListOf(0f)

    fun addSteps(numberOfSteps: Float) {
        allSteps.add(numberOfSteps)
    }

    fun timeElapsedInSeconds(): Int {
        return allSteps.size - 1
    }

    fun numberOfCompletedSteps(): Float {
        return allSteps[allSteps.size - 1]
    }
}