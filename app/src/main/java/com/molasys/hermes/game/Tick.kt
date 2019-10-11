package com.molasys.hermes.game

import android.os.Handler
import com.molasys.hermes.activities.DisplayData

class Tick(
    private val tickHandler: Handler,
    private val game: Game,
    private val updateDisplay: (DisplayData) -> Unit
) : Runnable {
    private var timeElapsedInSeconds = 1
    override fun run() {
        if(!game.isStarted()) {
            tickHandler.postDelayed(this, 1000)
            return
        }
        if(game.isFinished()) {
            game.stop()
            tickHandler.removeCallbacks(this)
            return
        }

        game.tick(timeElapsedInSeconds)
        timeElapsedInSeconds += 1
        tickHandler.postDelayed(this, 1000)

        val displayData = game.getDisplayData()
        displayData.timeElapsedInSeconds = timeElapsedInSeconds
        updateDisplay(displayData)
    }
}