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

        val animatedText = findViewById<TextView>(R.id.animatedText)
        val animatedImage = findViewById<ImageView>(R.id.img)

        // Image animation
        startAnimation(animatedImage, "translationY", 0f, -200f, 1000, DecelerateInterpolator())

        // Text animation
        startAnimation(animatedText, "alpha", 0f, 1f, 1000, AccelerateDecelerateInterpolator())

        // Transition to the main activity after a delay
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_DELAY)
    }

    companion object {
        private const val IMAGE_ANIMATION_DELAY: Long = 1000 // 1 second (adjust as needed)
        private const val SPLASH_DELAY: Long = 3000 // 3 seconds
        private const val TEXT_ANIMATION_DELAY: Long = 500 // 0.5 seconds (adjust as needed)
    }

    private fun startAnimation(
        view: ImageView, property: String, startValue: Float, endValue: Float,
        duration: Long, interpolator: DecelerateInterpolator
    ) {
        val animation = ObjectAnimator.ofFloat(view, property, startValue, endValue)
        animation.duration = duration
        animation.interpolator = interpolator
        Handler().postDelayed({ animation.start() }, IMAGE_ANIMATION_DELAY)
    }

    private fun startAnimation(
        view: TextView, property: String, startValue: Float, endValue: Float,
        duration: Long, interpolator: AccelerateDecelerateInterpolator
    ) {
        val animation = ObjectAnimator.ofFloat(view, property, startValue, endValue)
        animation.duration = duration
        animation.interpolator = interpolator
        Handler().postDelayed({ animation.start() }, TEXT_ANIMATION_DELAY)
    }
}