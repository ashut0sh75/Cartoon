package com.example.cartoon

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cartoon.databinding.ActivitySpScreenBinding

class Sp_Screen : AppCompatActivity() {
    private lateinit var binding : ActivitySpScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ThinkBot = "CARTOON!"
        Thread {
            for (letters in ThinkBot) {
                StringBuilder().append(letters)
                Thread.sleep(250)                                  //0.25 sec for each letter
                runOnUiThread {
                    binding.txtTitle.text = StringBuilder().toString()
                }
            }
            startActivity(Intent(this@Sp_Screen, MainActivity::class.java))
            finish()
        }.start()
    }
}