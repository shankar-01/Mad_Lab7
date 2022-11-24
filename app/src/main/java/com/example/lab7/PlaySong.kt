package com.example.lab7

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class PlaySong : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "here 191", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, "123")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("MyPlayer")
            .setContentText("Rington")
            .setContentIntent(pIntent)
            .build()
        startForeground(123, notification)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

}