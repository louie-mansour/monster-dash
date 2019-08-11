package com.example.hermes

class VirtualMonsterRun(private val monsterStepsPerSecond: Float) : VirtualRun() {
    fun addSteps() {
        this.addSteps(this.numberOfCompletedSteps() + monsterStepsPerSecond)
    }
}