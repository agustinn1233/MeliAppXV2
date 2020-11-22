package com.android.mercadolibre.meliappxv2.src.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.mercadolibre.meliappxv2.MainActivity
import com.android.mercadolibre.meliappxv2.R
import com.android.mercadolibre.meliappxv2.src.tools.utils.Constants.SPLASH_DELAY

class SplashScreen : AppCompatActivity() {
    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setBackgroundDrawable(null)

        // Methods to call
        initializeView()
        animateLogo()
        goToMainActivity()
    }

    private fun goToMainActivity() {
        // This method will take the user to main activity (productSearch)
        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }, SPLASH_DELAY.toLong()) // SPLASH_DELAY Defined in Constants
        // Not is the best practice for create a splash screen, this is for demo only.
        // Best in asyncTask, background.
    }

    private fun initializeView() {
        imageView = findViewById(R.id.imageViewSplashScreen)
    }

    private fun animateLogo() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_fade_in)
        animation.duration = SPLASH_DELAY.toLong()
        imageView!!.startAnimation(animation)
    }
}