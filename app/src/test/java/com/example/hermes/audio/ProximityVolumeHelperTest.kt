package com.example.hermes.audio

import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

class ProximityVolumeHelperTest {

    @Test
    fun increasingVolumeTooFarAwayIsZero() {
        val volume = increasingVolumeWithProximity(35f, 30)
        Assert.assertEquals(0f, volume)
    }

    @Test
    fun increasingVolumeAtThresholdIsZero() {
        val volume = increasingVolumeWithProximity(30f, 30)
        Assert.assertEquals(0f, volume)
    }

    @Test
    @Ignore("Will be corrected with volume transpose deep dive")
    fun increasingVolumeAtClosestDistanceIsOne() {
        val volume = increasingVolumeWithProximity(0f, 30)
        Assert.assertEquals(1f, volume)
    }

    @Test
    fun volumeIncreasesAsDistanceDecreases() {
        val volumeFar = increasingVolumeWithProximity(25f, 30)
        val volumeClose = increasingVolumeWithProximity(15f, 30)
        Assert.assertTrue(volumeFar < volumeClose)
    }

    @Test
    fun decreasingVolumeTooFarAwayIsOne() {
        val volume = decreasingVolumeWithProximity(35f, 30)
        Assert.assertEquals(1f, volume)
    }

    @Test
    @Ignore("Will be corrected with volume transpose deep dive")
    fun decreasingVolumeAtThresholdIsOne() {
        val volume = decreasingVolumeWithProximity(30f, 30)
        Assert.assertEquals(1f, volume)
    }

    @Test
    fun decreasingVolumeAtClosestDistanceIsZero() {
        val volume = decreasingVolumeWithProximity(0f, 30)
        Assert.assertEquals(0f, volume)
    }

    @Test
    fun volumeIncreasesAsDistanceIncreases() {
        val volumeFar = decreasingVolumeWithProximity(25f, 30)
        val volumeClose = decreasingVolumeWithProximity(15f, 30)
        Assert.assertTrue(volumeFar > volumeClose)
    }
}