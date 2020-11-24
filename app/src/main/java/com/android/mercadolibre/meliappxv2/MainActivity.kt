package com.android.mercadolibre.meliappxv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.android.mercadolibre.meliappxv2.src.ui.ProductSearchFragment

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        // Not use dark mode.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // Status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val productSearchFragment = ProductSearchFragment()
            fragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment, productSearchFragment)
                    .commitAllowingStateLoss()
        }
    }
}