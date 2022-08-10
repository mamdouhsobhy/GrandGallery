package com.example.grandgallery.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.grandgallery.authorize.presentation.ActivityAuth
import com.example.grandgallery.core.presentation.base.BaseActivity
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.databinding.ActivitySplashBinding
import com.example.grandgallery.homedonor.ActivityHomeDonor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    @Inject
    lateinit var sharedPref: SharedPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            if(sharedPref.getUserRole()=="Admin" && sharedPref.getFound().isEmpty()){
                val intent = Intent(this, ActivityHomeDonor::class.java)
                startActivity(intent)
                finish()

            }else {
                startActivity(Intent(this, ActivityAuth::class.java))
                finish()
            }

        }, SPLASH_TIME_OUT)

    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }
}