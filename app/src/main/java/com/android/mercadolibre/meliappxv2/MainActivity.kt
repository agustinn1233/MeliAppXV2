package com.android.mercadolibre.meliappxv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mercadolibre.meliappxv2.src.ui.ProductSearchFragment

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
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