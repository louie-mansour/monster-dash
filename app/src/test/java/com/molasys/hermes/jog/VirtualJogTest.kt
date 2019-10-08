package com.molasys.hermes.jog

import org.junit.Assert
import org.junit.Test

class VirtualJogTest {
    @Test
    fun constructor_creates_jog_with_zero_steps() {
        val virtualJog = VirtualJog()
        Assert.assertEquals(0f, virtualJog.distanceCovered())
        Assert.assertEquals(0, virtualJog.timeElapsedInSeconds())
    }

    @Test
    fun elapsed_time_in_seconds_increases_on_adding_steps() {
        val virtualJog = VirtualJog()
        virtualJog.addDistance(99f)
        virtualJog.addDistance(0f)
        virtualJog.addDistance(-5f)
        Assert.assertEquals(3f, virtualJog.timeElapsedInSeconds())
    }

    @Test
    fun number_of_completed_steps_is_latest_added_step() {
        val virtualJog = VirtualJog()
        virtualJog.addDistance(99f)
        virtualJog.addDistance(100f)
        virtualJog.addDistance(109f)
        Assert.assertEquals(109f, virtualJog.distanceCovered())
    }
}
