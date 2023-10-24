package com.example.cartoon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cartoon.utils.ToastUtils.getStringFromResource
import com.example.cartoon.databinding.ActivitySpScreenBinding

class Sp_Screen : AppCompatActivity() {
    private lateinit var binding : ActivitySpScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thinkBot = getStringFromResource(R.string.splash_screen_text)
        val stringBuilder1 = StringBuilder()
        Thread {
            for (letter in thinkBot) {
                stringBuilder1.append(letter)
                Thread.sleep(250)                                  //0.25 sec for each letter
                runOnUiThread {
                    binding.txtTitle.text = stringBuilder1.toString()
                }
            }
            startActivity(Intent(this@Sp_Screen, MainActivity::class.java))
            finish()
        }.start()
    }
}