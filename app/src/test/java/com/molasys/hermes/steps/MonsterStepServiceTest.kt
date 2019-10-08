package com.molasys.hermes.steps

import com.molasys.hermes.TestConfigs
import com.molasys.hermes.jog.VirtualJog
import com.molasys.hermes.monster.MonsterStepCalculator
import org.junit.Assert
import org.junit.Test

class MonsterStepServiceTest{

    private val virtualMonsterJog = VirtualJog()
        .addDistance(2f)
        .addDistance(4f)
        .addDistance(6f)
        .addDistance(8f)
        .addDistance(9f)
        .addDistance(10f)
    private val testConfigs = testConfigsFactory(2f, 35f, 5, 0.2f, 5)
    private val monsterStepsService = MonsterStepCalculator(testConfigs)
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
            .addDistance(10f)
        val monsterSteps = monsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 20f)
        Assert.assertEquals(10.4f, monsterSteps)
    }

    @Test
    fun monsterRampUpTakesPrecedenceOverCriticalZone() {
        val rampUpMonsterJog = VirtualJog()
            .addDistance(10f)
        val monsterSteps = monsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 12f)
        Assert.assertEquals(10.4f, monsterSteps)
    }

    @Test
    fun monsterRampUpValueCanBeReset() {
        val resetMonsterStepsService = MonsterStepCalculator(testConfigs)
        resetMonsterStepsService.startNewRampUp(10f)

        val rampUpMonsterJog = VirtualJog()
            .addDistance(10f)
            .addDistance(12f)
            .addDistance(14f)
            .addDistance(16f)
            .addDistance(18f)
            .addDistance(20f)
            .addDistance(22f)
            .addDistance(24f)
            .addDistance(26f)
            .addDistance(28f)
        var monsterSteps = resetMonsterStepsService.calculateMonsterSteps(rampUpMonsterJog, 40f)
        Assert.assertEquals(28f, monsterSteps)

        rampUpMonsterJog.addDistance(30f)
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
            .addDistance(2f)
            .addDistance(4f)
            .addDistance(6f)
            .addDistance(8f)
            .addDistance(9f)
            .addDistance(10f)

        var monsterSteps = monsterStepsService.calculateMonsterSteps(virtualMonsterJog, 10f)
        Assert.assertEquals(10f, monsterSteps)

        virtualMonsterJog.addDistance(10f)
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