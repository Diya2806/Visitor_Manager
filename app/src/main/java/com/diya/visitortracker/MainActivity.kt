package com.diya.visitortracker

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var progressBar: ProgressBar
         var progressCount = 0
         lateinit var timer: CountDownTimer
        try {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            progressBar = findViewById(R.id.progress)
            timer = object : CountDownTimer(1000000000,100){
                override fun onTick(millisUntilFinished: Long) {
                    progressCount++
                    progressBar.progress=progressCount
                    if(progressCount>100){
                        timer.cancel()
                        val intent = Intent(this@MainActivity, Login_Page::class.java)
                        startActivity(intent)
                        finish()

                    }
                }

                override fun onFinish() {
                    TODO("Not yet implemented")
                }
            }
            timer.start()


        }catch (e:Exception){
            println(e)
        }
    }
}