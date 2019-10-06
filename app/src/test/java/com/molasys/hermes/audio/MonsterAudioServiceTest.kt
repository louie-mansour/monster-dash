package com.molasys.hermes.audio

import android.media.MediaPlayer
import com.molasys.hermes.TestConfigs
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.ArgumentCaptor



class MonsterAudioServiceTest {

    private val mediaPlayer = Mockito.mock(MediaPlayer::class.java)
    private val monsterFootsteps = Mockito.spy(LoopingMonsterAudioPlayer(mediaPlayer))
    private val monsterVocalizations = Mockito.spy(LoopingMonsterAudioPlayer(mediaPlayer))
    private val backgroundNoise = Mockito.spy(LoopingMonsterAudioPlayer(mediaPlayer))
    private val monsterCriticalNoise = Mockito.spy(NonLoopingMonsterAudioPlayer(mediaPlayer))
    private val monsterBiteNoise = Mockito.spy(NonLoopingMonsterAudioPlayer(mediaPlayer))

    @Test
    fun playAudio() {
        val monsterAudioService = MonsterAudioService(
            monsterFootsteps,
            monsterVocalizations,
            backgroundNoise,
            monsterCriticalNoise,
            monsterBiteNoise,
            testConfigsFactory(30, 10, 5)
        )

        monsterAudioService.playAudio()

        Mockito.verify(monsterFootsteps, Mockito.times(1)).play()
        Mockito.verify(monsterVocalizations, Mockito.times(1)).play()
        Mockito.verify(backgroundNoise, Mockito.times(1)).play()
        Mockito.verify(monsterCriticalNoise, Mockito.times(1)).play(0)
    }

    @Test
    fun updateAudioWithoutCriticalNoise() {
        val testConfig = testConfigsFactory(30, 10, 5)
        monsterCriticalNoise.lastPlayed = testConfig.roarTimeBetween - 1

        val monsterAudioService = MonsterAudioService(
            monsterFootsteps,
            monsterVocalizations,
            backgroundNoise,
            monsterCriticalNoise,
            monsterBiteNoise,
            testConfig
        )

        val footStepsVolume = ArgumentCaptor.forClass<Float>(Float::class.java)
        val vocalizationsVolume = ArgumentCaptor.forClass<Float>(Float::class.java)
        val backgroundVolume = ArgumentCaptor.forClass<Float>(Float::class.java)

        monsterAudioService.updateAudio(testConfig.danger/2f, testConfig.roarTimeBetween)

        Mockito.verify(monsterFootsteps).setVolume(footStepsVolume.capture())
        Mockito.verify(monsterVocalizations).setVolume(vocalizationsVolume.capture())
        Mockito.verify(backgroundNoise).setVolume(backgroundVolume.capture())

        val furtherFootstepsVolume = footStepsVolume.value
        val furtherVocalizationsVolume = vocalizationsVolume.value
        val furtherBackgroundVolume = backgroundVolume.value

        monsterAudioService.updateAudio(testConfig.danger/3f, testConfig.roarTimeBetween)

        Mockito.verify(monsterFootsteps, Mockito.times(2)).setVolume(footStepsVolume.capture())
        Mockito.verify(monsterVocalizations, Mockito.times(2)).setVolume(vocalizationsVolume.capture())
        Mockito.verify(backgroundNoise, Mockito.times(2)).setVolume(backgroundVolume.capture())

        Assert.assertTrue(furtherFootstepsVolume < footStepsVolume.value)
        Assert.assertTrue(furtherVocalizationsVolume < vocalizationsVolume.value)
        Assert.assertTrue(furtherBackgroundVolume > backgroundVolume.value)
    }

    @Test
    fun criticalNoiseDoesNotPlayWhenOutsideOfCriticalDistance() {
        val testConfig = testConfigsFactory(30, 10, 5)
        monsterCriticalNoise.lastPlayed = 0

        val monsterAudioService = MonsterAudioService(
            monsterFootsteps,
            monsterVocalizations,
            backgroundNoise,
            monsterCriticalNoise,
            monsterBiteNoise,
            testConfig
        )

        monsterAudioService.updateAudio(testConfig.critical + 1f, 10)
        Mockito.verify(monsterCriticalNoise, Mockito.times(0)).play(Mockito.anyInt())
    }

    @Test
    fun criticalNoiseDoesNotPlayWhenLastCriticalNoiseWasTooRecent() {
        val testConfig = testConfigsFactory(30, 10, 5)
        monsterCriticalNoise.lastPlayed = 1

        val monsterAudioService = MonsterAudioService(
            monsterFootsteps,
            monsterVocalizations,
            backgroundNoise,
            monsterCriticalNoise,
            monsterBiteNoise,
            testConfig
        )

        monsterAudioService.updateAudio(testConfig.critical - 1f, testConfig.roarTimeBetween)
        Mockito.verify(monsterCriticalNoise, Mockito.times(0)).play(Mockito.anyInt())
    }

    @Test
    fun criticalNoisePlaysWhenLastCriticalNoiseWasNotTooRecent() {
        val testConfig = testConfigsFactory(30, 10, 5)
        monsterCriticalNoise.lastPlayed = 1

        val monsterAudioService = MonsterAudioService(
            monsterFootsteps,
            monsterVocalizations,
            backgroundNoise,
            monsterCriticalNoise,
            monsterBiteNoise,
            testConfig
        )

        monsterAudioService.updateAudio(testConfig.critical - 1f, testConfig.roarTimeBetween + 1)
        Mockito.verify(monsterCriticalNoise, Mockito.times(1)).play(Mockito.anyInt())
    }

    @Test
    fun criticalNoisePlaysWhenLastCriticalNoiseIsSetToZero() {
        val testConfig = testConfigsFactory(30, 10, 5)
        monsterCriticalNoise.lastPlayed = 0

        val monsterAudioService = MonsterAudioService(
            monsterFootsteps,
            monsterVocalizations,
            backgroundNoise,
            monsterCriticalNoise,
            monsterBiteNoise,
            testConfig
        )

        monsterAudioService.updateAudio(testConfig.critical - 1f, testConfig.roarTimeBetween/2)
        Mockito.verify(monsterCriticalNoise, Mockito.times(1)).play(Mockito.anyInt())
    }

    private fun testConfigsFactory(danger: Int, critical: Int, roarTimeBetween: Int): TestConfigs {
        return TestConfigs(
            danger,
            critical,
            roarTimeBetween,
            0f,
            0f,
            0f,
            0,
            0,
            0
        )
    }
}