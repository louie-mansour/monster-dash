package com.molasys.hermes.steps

import com.molasys.hermes.TestConfigs
import com.molasys.hermes.jog.VirtualJog
import org.junit.Assert
import org.junit.Test

class MonsterStepServiceTest{

    private val virtualMonsterJog = VirtualJog()
        .addSteps(2f)
        .addSteps(4f)
        .addSteps(6f)
        .addSteps(8f)
        .addSteps(9f)
        .addSteps(10f)
    private val testConfigs = testConfigsFactory(2f, 35f, 5, 0.2f, 5)

    @Test
    fun monsterRegularDistanceBehind() {
        val monsterSteps = calculateMonsterSteps(virtualMonsterJog, testConfigs, 20f)
        Assert.assertEquals(12f, monsterSteps)
    }

    @Test
    fun monsterTooFarBehind() {
        val monsterSteps = calculateMonsterSteps(virtualMonsterJog, testConfigs, 50f)
        Assert.assertEquals(15f, monsterSteps)
    }

    @Test
    fun monsterVeryCloseInRubberBandingZone() {
        val monsterSteps = calculateMonsterSteps(virtualMonsterJog, testConfigs, 14f)
        Assert.assertEquals(11.8f, monsterSteps)
    }

    @Test
    fun monsterRampUpValueIsAsExpected() {
        val rampUpMonsterJog = VirtualJog()
            .addSteps(10f)
        val monsterSteps = calculateMonsterSteps(rampUpMonsterJog, testConfigs, 20f)
        Assert.assertEquals(10.4f, monsterSteps)
    }

    @Test
    fun monsterRampUpTakesPrecedenceOverCriticalZone() {
        val rampUpMonsterJog = VirtualJog()
            .addSteps(10f)
        val monsterSteps = calculateMonsterSteps(rampUpMonsterJog, testConfigs, 12f)
        Assert.assertEquals(10.4f, monsterSteps)
    }

    private fun testConfigsFactory(stepsPerSecond: Float, maxDistance: Float, criticalDistance: Int, criticalDistanceRubberBanding: Float, rampUpTime: Int): TestConfigs {
        return TestConfigs(
            0,
            criticalDistance,
            0,
            maxDistance,
            criticalDistanceRubberBanding,
            stepsPerSecond,
            0,
            0,
            rampUpTime
        )
    }
}