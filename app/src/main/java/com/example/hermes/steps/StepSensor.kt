package com.example.hermes.steps

import android.hardware.SensorEvent

class StepSensor {
    var firstStepInSessionSinceLastReboot: Float? = null
    var numberOfStepsSinceLastReboot: Float? = null

    fun numberOfStepsInSession(): Float? {
        val firstStepInSessionSinceLastReboot = firstStepInSessionSinceLastReboot
        val numberOfStepsSinceLastReboot = numberOfStepsSinceLastReboot

        if (null == firstStepInSessionSinceLastReboot || null == numberOfStepsSinceLastReboot) {
            return null
        }
        return numberOfStepsSinceLastReboot - firstStepInSessionSinceLastReboot + 1
    }

    fun setNumberOfSteps(event: SensorEvent) {
        val value = event.values[0]
        if (null == firstStepInSessionSinceLastReboot) {
            firstStepInSessionSinceLastReboot = value
        }
        numberOfStepsSinceLastReboot = value
    }

    fun clear() {
        firstStepInSessionSinceLastReboot = null
        numberOfStepsSinceLastReboot = null
    }
}