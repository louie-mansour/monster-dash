package com.example.hermes.jog

import org.junit.Assert
import org.junit.Test

class VirtualJogTest {
    @Test
    fun constructor_creates_jog_with_zero_steps() {
        val virtualJog = VirtualJog()
        Assert.assertEquals(0f, virtualJog.numberOfCompletedSteps())
        Assert.assertEquals(0, virtualJog.timeElapsedInSeconds())
    }

    @Test
    fun elapsed_time_in_seconds_increases_on_adding_steps() {
        val virtualJog = VirtualJog()
        virtualJog.addSteps(99f)
        virtualJog.addSteps(0f)
        virtualJog.addSteps(-5f)
        Assert.assertEquals(3f, virtualJog.timeElapsedInSeconds())
    }

    @Test
    fun number_of_completed_steps_is_latest_added_step() {
        val virtualJog = VirtualJog()
        virtualJog.addSteps(99f)
        virtualJog.addSteps(100f)
        virtualJog.addSteps(109f)
        Assert.assertEquals(109f, virtualJog.numberOfCompletedSteps())
    }
}
