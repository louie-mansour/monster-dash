package com.molasys.hermes.steps

import com.molasys.hermes.StepSensor
import org.junit.Assert
import org.junit.Test

class StepSensorTest {
    @Test
    fun nullStepsInSessionOnInitialization() {
        val stepSensor = StepSensor()
        Assert.assertEquals(null, stepSensor.numberOfStepsInSession())
    }

    @Test
    fun setNumberOfStepsSetsTheBaselineWhenCalledOnce() {
        val stepSensor = StepSensor()
        stepSensor.setNumberOfSteps(123f)
        Assert.assertEquals(0f, stepSensor.numberOfStepsInSession())
    }

    @Test
    fun setNumberOfStepsAddsToTheBaselineWhenCalledAgain() {
        val stepSensor = StepSensor()
        stepSensor.setNumberOfSteps(123f)
        stepSensor.setNumberOfSteps(133f)
        Assert.assertEquals(10f, stepSensor.numberOfStepsInSession())
    }

    @Test
    fun clearResetsTheNumberOfStepsToNull() {
        val stepSensor = StepSensor()
        stepSensor.setNumberOfSteps(123f)
        stepSensor.clear()
        Assert.assertEquals(null, stepSensor.numberOfStepsInSession())
    }

    @Test
    fun setNumberOfStepsAfterClearCreatesANewBaseline() {
        val stepSensor = StepSensor()
        stepSensor.setNumberOfSteps(123f)
        stepSensor.clear()
        stepSensor.setNumberOfSteps(234f)
        Assert.assertEquals(0f, stepSensor.numberOfStepsInSession())
        stepSensor.setNumberOfSteps(264f)
        Assert.assertEquals(30f, stepSensor.numberOfStepsInSession())
        stepSensor.setNumberOfSteps(267f)
        Assert.assertEquals(33f, stepSensor.numberOfStepsInSession())
    }
}