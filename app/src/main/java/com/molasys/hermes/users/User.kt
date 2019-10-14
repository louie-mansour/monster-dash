package com.molasys.hermes.users

import com.molasys.hermes.jog.VirtualJog
import com.molasys.hermes.StepSensor
import com.molasys.hermes.jog.CoversDistance

class User(private val stepSensor: StepSensor) : CoversDistance {
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

    override fun distanceCovered(): Float {
        return virtualJog.distanceCovered()
    }
}