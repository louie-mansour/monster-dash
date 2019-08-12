package com.example.hermes

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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensorManager: SensorManager? = null
    val stepSensor: StepSensor = StepSensor()
    var virtualRun: VirtualRun = VirtualRun()
    var virtualMonsterRun: VirtualRun = VirtualRun()
    var monsterConfig: MonsterRunConfig? = null
    var eventQueue: Queue<Event>? = null
    var monsterAudio: MonsterAudio? = null
    var timeLengthInSeconds: Int? = null
    var isActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_GAME)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (!isActive) {
                    mainHandler.postDelayed(this, 1000)
                    return
                }

                val numberOfStepsInSession = stepSensor.numberOfStepsInSession()
                val timeElapsedInSeconds = virtualRun.timeElapsedInSeconds()

                if(eventQueue!!.isEmpty()) {
                    mainHandler.removeCallbacks(this)
                    return
                }

                if(numberOfStepsInSession == null) {
                    mainHandler.postDelayed(this, 1000)
                    return
                }

                if(timeElapsedInSeconds >= eventQueue!!.peek().timeElapsedInSeconds) {
                    eventQueue!!.poll().audio.start()
                }

                virtualRun.addSteps(numberOfStepsInSession)
                var monsterSteps = virtualMonsterRun!!.numberOfCompletedSteps() + monsterConfig!!.stepsPerSecond
                var monsterStepsBehind = numberOfStepsInSession - monsterSteps
                if(numberOfStepsInSession - monsterSteps > monsterConfig!!.maxDistance) {
                    monsterSteps = numberOfStepsInSession - monsterConfig!!.maxDistance
                } else if(monsterStepsBehind < criticalDistance.text.toString().toFloat()) {
                    monsterSteps -= monsterConfig!!.criticalDistanceRubberBanding
                }
                monsterStepsBehind = numberOfStepsInSession - monsterSteps
                virtualMonsterRun!!.addSteps(monsterSteps)
                val numberOfCompletedSteps = virtualRun.numberOfCompletedSteps()
                monsterAudio!!.setMonsterAudio(monsterStepsBehind)
                yourSteps.text = numberOfCompletedSteps.toString()
                monsterStepsBehindYou.text = monsterStepsBehind.toString()
                time.text = String.format("%ds", timeElapsedInSeconds)
                percent.text = String.format("%,d%%", 100 * timeElapsedInSeconds / timeLengthInSeconds!!)
                eventQueueSize.text = String.format("%d", eventQueue!!.size)

                mainHandler.postDelayed(this, 1000)
            }
        })
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if (!isActive) {
            return
        }
        stepSensor.setNumberOfSteps(event)
    }

    fun startRun(view: View) {
        val monsterAudioConfig = MonsterAudioConfig(
            dangerDistance.text.toString().toInt(),
            criticalDistance.text.toString().toInt(),
            bigRoarRepeats.text.toString().toInt()
        )

        monsterConfig = MonsterRunConfig(
            maxDistance.text.toString().toFloat(),
            criticalDecrease.text.toString().toFloat(),
            monsterStepsPerSecond.text.toString().toFloat()
        )

        val runConfig = RunConfig(
            runLengthInMinutes.text.toString().toInt(),
            timeBetweenProgressUpdates.text.toString().toInt()
        )
        timeLengthInSeconds = runLengthInMinutes.text.toString().toInt() * 60
        monsterAudio = MonsterAudio(applicationContext, monsterAudioConfig)
        eventQueue = eventQueueFactory(applicationContext, runConfig)
        doStartRun()
    }

    private fun doStartRun() {
        stepSensor.clear()
        isActive = true
        monsterAudio!!.reset()
    }
}