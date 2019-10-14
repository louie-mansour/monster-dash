package com.molasys.hermes.activities

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
import com.molasys.hermes.R
import com.molasys.hermes.StepSensor
import com.molasys.hermes.TestConfigs
import com.molasys.hermes.audio.LoopingAudio
import com.molasys.hermes.audio.ProgressAudioService
import com.molasys.hermes.events.EventQueueFactory
import com.molasys.hermes.game.Game
import com.molasys.hermes.game.GameConfigs
import com.molasys.hermes.game.Tick
import com.molasys.hermes.monster.DINOSAUR
import com.molasys.hermes.monster.MonsterFactory
import com.molasys.hermes.surroundings.Background
import com.molasys.hermes.users.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private val stepSensor: StepSensor = StepSensor()
    private var runTimeInSeconds = 0

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
        val tickHandler = Handler(Looper.getMainLooper())
        val testConfigs = setupTestConfigs()
        runTimeInSeconds = testConfigs.runTimeInSeconds

        val user = User(stepSensor)
        val dinosaur = MonsterFactory(applicationContext, testConfigs).make(DINOSAUR)
        val eventQueue = EventQueueFactory(ProgressAudioService(applicationContext)).make(testConfigs)
        val background = Background(LoopingAudio(MediaPlayer.create(applicationContext, R.raw.dinosaur_background_quieter)))
        val gameConfigs = GameConfigs(testConfigs.danger, testConfigs.critical, testConfigs.criticalDistanceRubberBanding, testConfigs.maxDistance)
        val game = Game(user, dinosaur, eventQueue, background, gameConfigs)

        tickHandler.post(Tick(tickHandler, game, ::updateDisplay))
    }

    fun updateDisplay(displayData: DisplayData) {
        yourSteps.text = displayData.numberOfCompletedSteps.toString()
        monsterStepsBehindYou.text = displayData.monsterStepsBehind.toString()
        time.text = String.format("%ds", displayData.timeElapsedInSeconds)
        percent.text = String.format("%,d%%", 100 * displayData.timeElapsedInSeconds / runTimeInSeconds)
        eventQueueSize.text = eventQueueSize.toString()
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
            timeBetweenProgressUpdates.text.toString().toInt(),
            rampUpTime.text.toString().toInt()
        )
    }
}