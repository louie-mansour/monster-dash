package com.molasys.hermes.users

import com.molasys.hermes.jog.VirtualJog
import com.molasys.hermes.StepSensor

class User(private val stepSensor: StepSensor) {
    private val virtualJog: VirtualJog = VirtualJog()

    init {
        stepSensor.clear()
    }

    fun isRunning(): Boolean {
        return null != stepSensor.numberOfStepsInSession()
    }

    fun updateDistanceUsingSensor() {
        val numberOfStepsInSession = stepSensor.numberOfStepsInSession()
        if (null != numberOfStepsInSession) {
            virtualJog.addDistance(numberOfStepsInSession)
        }
    }

    fun distanceCovered(): Float {
        return virtualJog.distanceCovered()
    }
}