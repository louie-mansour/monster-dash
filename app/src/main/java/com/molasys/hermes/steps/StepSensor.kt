package com.molasys.hermes.steps

class StepSensor {
    private var firstStepInSessionSinceLastReboot: Float? = null
    private var numberOfStepsSinceLastReboot: Float? = null

    fun numberOfStepsInSession(): Float? {
        val firstStepInSessionSinceLastReboot = firstStepInSessionSinceLastReboot
        val numberOfStepsSinceLastReboot = numberOfStepsSinceLastReboot

        if (null == firstStepInSessionSinceLastReboot || null == numberOfStepsSinceLastReboot) {
            return null
        }
        return numberOfStepsSinceLastReboot - firstStepInSessionSinceLastReboot
    }

    fun setNumberOfSteps(steps: Float) {
        if (null == firstStepInSessionSinceLastReboot) {
            firstStepInSessionSinceLastReboot = steps
        }
        numberOfStepsSinceLastReboot = steps
    }

    fun clear() {
        firstStepInSessionSinceLastReboot = null
        numberOfStepsSinceLastReboot = null
    }
}