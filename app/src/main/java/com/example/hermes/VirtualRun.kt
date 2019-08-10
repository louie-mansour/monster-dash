package com.example.hermes

class VirtualRun {
    var isActive: Boolean = false
    private var allSteps: MutableList<Float> = mutableListOf()

    init {
        allSteps.add(0f)
    }

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