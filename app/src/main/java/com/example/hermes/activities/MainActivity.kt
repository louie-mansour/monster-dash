package com.example.hermes.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.hermes.R
import com.example.hermes.TestConfigs
import com.example.hermes.audio.LoopingMonsterAudioPlayer
import com.example.hermes.audio.MonsterAudioService
import com.example.hermes.audio.NonLoopingMonsterAudioPlayer
import com.example.hermes.audio.ProgressAudioPlayerService
import com.example.hermes.events.EventQueueService
import com.example.hermes.jog.VirtualJog
import com.example.hermes.steps.StepSensor
import com.example.hermes.steps.calculateMonsterSteps
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    val stepSensor: StepSensor = StepSensor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        stepSensor.setNumberOfSteps(event.values[0])
    }

    fun onClickStartJog(view: View) {
        val testConfigs = setupTestConfigs()
        val monsterFootsteps = LoopingMonsterAudioPlayer(MediaPlayer.create(applicationContext, R.raw.dinosaur_steps_amp))
        val monsterVocalizations = LoopingMonsterAudioPlayer(MediaPlayer.create(applicationContext, R.raw.dinosaur_vocalization))
        val backgroundNoise = LoopingMonsterAudioPlayer(MediaPlayer.create(applicationContext, R.raw.dinosaur_background_quieter))
        val monsterCriticalNoise = NonLoopingMonsterAudioPlayer(MediaPlayer.create(applicationContext, R.raw.dinosaur_big_roar))
        val monsterAudioService = MonsterAudioService(
            monsterFootsteps,
            monsterVocalizations,
            backgroundNoise,
            monsterCriticalNoise,
            testConfigs)
        val progressAudioService = ProgressAudioPlayerService(applicationContext)
        val eventQueueService = EventQueueService(progressAudioService)
        val eventQueue = eventQueueService.eventQueueFactory(testConfigs)

        stepSensor.clear()
        monsterAudioService.playAudio()

        val mainHandler = Handler(Looper.getMainLooper())
        var virtualJog = VirtualJog()
        var virtualMonsterJog = VirtualJog()


        mainHandler.post(object : Runnable {
            override fun run() {
                val numberOfStepsInSession = stepSensor.numberOfStepsInSession()
                val timeElapsedInSeconds = virtualJog.timeElapsedInSeconds()

                if(numberOfStepsInSession == null) {
                    mainHandler.postDelayed(this, 1000); return
                }
                if(eventQueue.isEmpty()) {
                    mainHandler.removeCallbacks(this); return
                }
                if(timeElapsedInSeconds >= eventQueue.peek().timeElapsedInSeconds) {
                    eventQueue.poll().audio.start()
                }

                virtualJog = virtualJog.addSteps(numberOfStepsInSession)

                val monsterSteps = calculateMonsterSteps(virtualJog, testConfigs, numberOfStepsInSession)
                virtualMonsterJog = virtualMonsterJog.addSteps(monsterSteps)

                // set monster audio to new levels
                val monsterStepsBehind = numberOfStepsInSession - monsterSteps
                monsterAudioService.updateAudio(monsterStepsBehind, timeElapsedInSeconds)

                // update UI
                val numberOfCompletedSteps = virtualJog.numberOfCompletedSteps()
                yourSteps.text = numberOfCompletedSteps.toString()
                monsterStepsBehindYou.text = monsterStepsBehind.toString()
                time.text = String.format("%ds", timeElapsedInSeconds)
                percent.text = String.format("%,d%%", 100 * timeElapsedInSeconds / testConfigs.runTimeInSeconds)
                eventQueueSize.text = String.format("%d", eventQueue.size)

                mainHandler.postDelayed(this, 1000)
            }
        })
    }

    private fun setupTestConfigs(): TestConfigs {
        return TestConfigs(
            dangerDistance.text.toString().toInt(),
            criticalDistance.text.toString().toInt(),
            bigRoarRepeats.text.toString().toInt(),
            maxDistance.text.toString().toFloat(),
            criticalDecrease.text.toString().toFloat(),
            monsterStepsPerSecond.text.toString().toFloat(),
            runLengthInMinutes.text.toString().toInt() * 60,
            timeBetweenProgressUpdates.text.toString().toInt()
        )
    }
}