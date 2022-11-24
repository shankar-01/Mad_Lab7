package com.example.lab7

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class RingAlarm : AppCompatActivity() {
    lateinit var mp:MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring_alarm)
        mp = MediaPlayer.create(this, R.raw.rington)
        mp.start()
    }
    fun stopAlarm(v: View){
        mp.stop()
        finish()
    }
}