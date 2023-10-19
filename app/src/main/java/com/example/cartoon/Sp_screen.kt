package com.example.cartoon

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Sp_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_screen)

        val textView = findViewById<TextView>(R.id.textView);
        val ThinkBot = "CARTOON!"
        val stringBuilder1 = StringBuilder()
        val t1 = Thread {
            for (letters in ThinkBot) {
                stringBuilder1.append(letters)
                Thread.sleep(250)
                runOnUiThread {
                    textView.text = stringBuilder1.toString()
                }
            }
            val jump: Intent = Intent(this@Sp_Screen, MainActivity::class.java)
            startActivity(jump)
            finish()
        }.start()
    }
}