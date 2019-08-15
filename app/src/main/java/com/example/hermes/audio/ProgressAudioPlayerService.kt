package com.example.hermes.audio

import android.content.Context
import android.media.MediaPlayer
import com.example.hermes.R

open class ProgressAudioPlayerService(private val context: Context) {
    open fun findAudioFile(percentComplete: Double): Pair<MediaPlayer, String> {
        when {
            percentComplete < 5.0 -> return Pair(MediaPlayer.create(context, R.raw.one_percent_complete), "one_percent_complete")
            percentComplete < 10.0 -> return Pair(MediaPlayer.create(context, R.raw.five_percent_complete), "five_percent_complete")
            percentComplete < 15.0 -> return Pair(MediaPlayer.create(context, R.raw.ten_percent_complete), "ten_percent_complete")
            percentComplete < 20.0 -> return Pair(MediaPlayer.create(context, R.raw.fifteen_percent_complete), "fifteen_percent_complete")
            percentComplete < 25.0 -> return Pair(MediaPlayer.create(context, R.raw.twenty_percent_complete), "twenty_percent_complete")
            percentComplete < 30.0 -> return Pair(MediaPlayer.create(context, R.raw.twenty_five_percent_complete), "twenty_five_percent_complete")
            percentComplete < 35.0 -> return Pair(MediaPlayer.create(context, R.raw.thirty_percent_complete), "thirty_percent_complete")
            percentComplete < 40.0 -> return Pair(MediaPlayer.create(context, R.raw.thrity_five_percent_complete), "thrity_five_percent_complete")
            percentComplete < 45.0 -> return Pair(MediaPlayer.create(context, R.raw.forty_percent_complete), "forty_percent_complete")
            percentComplete < 50.0 -> return Pair(MediaPlayer.create(context, R.raw.forty_five_percent_complete), "forty_five_percent_complete")
            percentComplete < 55.0 -> return Pair(MediaPlayer.create(context, R.raw.fifty_percent_complete), "fifty_percent_complete")
            percentComplete < 60.0 -> return Pair(MediaPlayer.create(context, R.raw.fifty_five_percent_complete), "fifty_five_percent_complete")
            percentComplete < 65.0 -> return Pair(MediaPlayer.create(context, R.raw.sixty_percent_complete), "sixty_percent_complete")
            percentComplete < 70.0 -> return Pair(MediaPlayer.create(context, R.raw.sixty_five_percent_complete), "sixty_five_percent_complete")
            percentComplete < 75.0 -> return Pair(MediaPlayer.create(context, R.raw.seventy_percent_complete), "seventy_percent_complete")
            percentComplete < 80.0 -> return Pair(MediaPlayer.create(context, R.raw.seventy_five_percent_complete), "seventy_five_percent_complete")
            percentComplete < 85.0 -> return Pair(MediaPlayer.create(context, R.raw.eighty_percent_complete), "eighty_percent_complete")
            percentComplete < 90.0 -> return Pair(MediaPlayer.create(context, R.raw.eighty_five_percent_complete), "eighty_five_percent_complete")
            percentComplete < 95.0 -> return Pair(MediaPlayer.create(context, R.raw.ninety_percent_complete), "ninety_percent_complete")
            percentComplete < 100.0 -> return Pair(MediaPlayer.create(context, R.raw.ninety_five_percent_complete), "ninety_five_percent_complete")
            else -> return Pair(MediaPlayer.create(context, R.raw.one_hundred_percent_complete), "one_hundred_percent_complete")
        }
    }
}