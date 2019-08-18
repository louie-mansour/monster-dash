package com.example.hermes.steps

import com.example.hermes.TestConfigs
import com.example.hermes.jog.VirtualJog
import org.junit.Assert
import org.junit.Test

class MonsterStepServiceTest{

    private val virtualJog = VirtualJog().addSteps(10f)
    private val testConfigs = testConfigsFactory(2f, 35f, 5, 0.2f)

    @Test
    fun monsterRegularDistanceBehind() {
        val monsterSteps = calculateMonsterSteps(virtualJog, testConfigs, 20f)
        Assert.assertEquals(12f, monsterSteps)
    }

    @Test
    fun monsterTooFarBehind() {
        val monsterSteps = calculateMonsterSteps(virtualJog, testConfigs, 50f)
        Assert.assertEquals(15f, monsterSteps)
    }

    @Test
    fun monsterVeryCloseInRubberBandingZone() {
        val monsterSteps = calculateMonsterSteps(virtualJog, testConfigs, 14f)
        Assert.assertEquals(11.8f, monsterSteps)
    }

    private fun testConfigsFactory(stepsPerSecond: Float, maxDistance: Float, criticalDistance: Int, criticalDistanceRubberBanding: Float): TestConfigs {
        return TestConfigs(
            0,
            criticalDistance,
            0,
            maxDistance,
            criticalDistanceRubberBanding,
            stepsPerSecond,
            0,
            0
        )
    }
}