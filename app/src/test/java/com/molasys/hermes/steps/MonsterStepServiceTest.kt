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
    private val monsterStepsService = MonsterStepsService(testConfigs)
    @Test
    fun monsterRegularDistanceBehind() {
        val monsterSteps = monsterStepsService.calculateMonsterSteps(virtualMonsterJog, 20f)
        Assert.assertEquals(12f, monsterSteps)
    }

    @Test
    fun monsterTooFarBehind() {
        val monsterSteps = monsterStepsService.calculateMonsterSteps(virtualMonsterJog, 50f)
        Assert.assertEquals(15f, monsterSteps)
    }

    @Test
    fun monsterVeryCloseInRubberBandingZone() {
        val monsterSteps = monsterStepsService.calculateMonsterSteps(virtualMonsterJog, 14f)
        Assert.assertEquals(11.8f, monsterSteps)
    }

    @Test
    fun monsterRampUpStartsWithZeroSteps() {
        val rampUpMonsterJog = VirtualJog()
        val monsterSteps = monsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 20f)
        Assert.assertEquals(0f, monsterSteps)
    }

    @Test
    fun monsterRampUpValueIsAsExpected() {
        val rampUpMonsterJog = VirtualJog()
            .addSteps(10f)
        val monsterSteps = monsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 20f)
        Assert.assertEquals(10.4f, monsterSteps)
    }

    @Test
    fun monsterRampUpTakesPrecedenceOverCriticalZone() {
        val rampUpMonsterJog = VirtualJog()
            .addSteps(10f)
        val monsterSteps = monsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 12f)
        Assert.assertEquals(10.4f, monsterSteps)
    }

    @Test
    fun monsterRampUpValueCanBeReset() {
        val resetMonsterStepsService = MonsterStepsService(testConfigs)
        resetMonsterStepsService.startNewRampUp(10f)

        val rampUpMonsterJog = VirtualJog()
            .addSteps(10f)
            .addSteps(12f)
            .addSteps(14f)
            .addSteps(16f)
            .addSteps(18f)
            .addSteps(20f)
            .addSteps(22f)
            .addSteps(24f)
            .addSteps(26f)
            .addSteps(28f)
        var monsterSteps = resetMonsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 40f)
        Assert.assertEquals(28f, monsterSteps)

        rampUpMonsterJog.addSteps(30f)
        monsterSteps = resetMonsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 40f)
        Assert.assertEquals(30.4f, monsterSteps)
    }

    @Test
    fun monsterDoesNotRunPastRunner() {
        val monsterSteps = monsterStepsService.calculateMonsterSteps(virtualMonsterJog, 10f)
        Assert.assertEquals(10f, monsterSteps)
    }

    @Test
    fun monsterRampUpStartsAfterSharingSameSpotAsRunner() {
        val virtualMonsterJog = VirtualJog()
            .addSteps(2f)
            .addSteps(4f)
            .addSteps(6f)
            .addSteps(8f)
            .addSteps(9f)
            .addSteps(10f)

        var monsterSteps = monsterStepsService.calculateMonsterSteps(virtualMonsterJog, 10f)
        Assert.assertEquals(10f, monsterSteps)

        virtualMonsterJog.addSteps(10f)
        monsterSteps = monsterStepsService.calculateMonsterSteps(virtualMonsterJog, 20f)
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