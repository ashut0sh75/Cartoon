package com.example.cartoon

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView


class Sp_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_screen)

        val animatedText = findViewById<TextView>(R.id.animatedText)
        val animatedImage = findViewById<ImageView>(R.id.img)

        val imageAnimation = ObjectAnimator.ofFloat(
            animatedImage,
            "translationY",
            0f,
            -200f // Adjust the value to determine how much the image moves up
        )
        imageAnimation.duration = 1000 // 1 second
        imageAnimation.interpolator = DecelerateInterpolator()

        // Start the image animation with a slight delay
        Handler().postDelayed({
            imageAnimation.start()
        }, IMAGE_ANIMATION_DELAY)


        // Text animation
        val fadeInAnimation = ObjectAnimator.ofFloat(animatedText, "alpha", 0f, 1f)
        fadeInAnimation.duration = 1000 // 1 second
        fadeInAnimation.interpolator = AccelerateDecelerateInterpolator()

        // Start the text animation with a slight delay
        Handler().postDelayed({
            fadeInAnimation.start()
        }, TEXT_ANIMATION_DELAY)

        // Delay for a few seconds and then transition to the main activity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }

    companion object {
        private const val IMAGE_ANIMATION_DELAY: Long = 1000 // 1 second (adjust as needed)
        private const val SPLASH_DELAY: Long = 3000 // 3 seconds
        private const val TEXT_ANIMATION_DELAY: Long = 500 // 0.5 seconds (adjust as needed)
    }
}
