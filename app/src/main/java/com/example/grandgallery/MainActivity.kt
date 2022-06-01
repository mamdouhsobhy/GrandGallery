package com.example.grandgallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.grandgallery.core.presentation.base.BaseActivity
import com.example.grandgallery.databinding.ActivityMainBinding
import com.example.grandgallery.gallerygrand.presentation.GalleryGrandActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, GalleryGrandActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)

    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }
}