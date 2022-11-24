package com.example.lab7

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.sql.Time
import java.util.Scanner
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    var hour = 0
    var minute = 0
    var second = 0
    var flag = false
    var toggle = false
    lateinit var serviceIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        //task1
        setContentView(R.layout.activity_main)
        task1()
         */

        /*
        //task2
        setContentView(R.layout.task2)
        task2()
         */
        //task3
        setContentView(R.layout.task3)
        task3()

        //tas4
        /*setContentView(R.layout.task4)

         */
    }
    fun networkDemoVolley(){
        //create queue
        val queue = Volley.newRequestQueue(this)

        //create request (get/post, url:String, successListener, failListener)
        val url = "https://www.tutorialspoint.com/json/data.json"
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener {
                val tv = findViewById<TextView>(R.id.textView2)
                tv.text = it;
            },
            Response.ErrorListener {
                val tv = findViewById<TextView>(R.id.textView2)
                tv.text = "Error"
            })
        queue.add(request)
    }
    fun networkDemo(){
        //create url
        val thread = Thread {
            val url = URL("https://www.tutorialspoint.com/json/data.json");

            //open connection
            try {
                val con = url.openConnection() as HttpURLConnection
                con.connect()
                val reader = BufferedReader(InputStreamReader(con.inputStream))
                var line = ""
                val tv = findViewById<TextView>(R.id.textView2)
                while (true) {
                    line = reader.readLine()
                    if (line== null){
                        break
                    }
                    tv.post {
                        tv.text = tv.text.toString() + line
                    }

                }

            } catch (e: Exception) {
                Log.d("exception ", e.stackTraceToString())
            }
        }
        thread.start()
    }
    fun task1() {
            val tv: TextView = findViewById(R.id.textView)
            val thread= Thread {
                while (true) {
                    if(flag) {
                        update()
                        tv.post {
                            tv.text = getTime()
                        }
                    }
                    Thread.sleep(1000)
                }
            }
            thread.start()

    }
    fun start(v:View){
        flag = true;
    }
    fun stop(v:View){
        flag = false;
    }
    fun reset(v:View){
        second = 0
        hour = 0
        minute = 0
        val tv: TextView = findViewById(R.id.textView)
        tv.text = getTime()
    }
    fun update(){
        second ++;
        if(second >=60){
            minute ++;
            second = 0;
            if(minute>=60){
                minute = 0;
                hour ++;
            }
        }
    }
    fun getTime():String{
        return format(hour)+":"+format(minute)+":"+format(second)
    }
    fun format(time:Int):String{
        if(time>9)
        {
            return ""+time
        }
        return "0"+time;
    }
    fun task2(){
        val img:ImageView = findViewById(R.id.imageView)
        val thread= Thread {
            while (true) {
                if(toggle) {
                    update()
                    img.post {
                        img.rotation = (img.rotation+10)%360
                    }
                }
                Thread.sleep(500)
            }
        }
        thread.start()
    }
    fun toggle(v:View){
        toggle = !toggle
    }
    fun setAlarm(v:View){
        val picker:TimePickerDialog = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
            Toast.makeText(this, "hour " + i + " minutes " + i2, Toast.LENGTH_SHORT).show()
            val alarm = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent1 : Intent = Intent(this, RingAlarm::class.java)
            val pIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_IMMUTABLE)

            val time: Long = Time(i, i2, 0).time; //calculate this
            alarm.set(AlarmManager.RTC_WAKEUP, time, pIntent)
        }, 12, 0, true)
        picker.show()

    }
    fun task3(){
        toggle = false;
        serviceIntent = Intent(this, PlaySong::class.java)
    }
    fun playOrPause(v:View){

        if(!toggle){
            toggle = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            }
            else{
                startService(serviceIntent)
            }
            val mp = MediaPlayer.create(this, R.raw.rington);
            mp.start();

        }
        else{
            toggle = false;
            stopService(serviceIntent)
        }
    }
}
