package com.example.grandgallery.baseclass

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GrandGalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
    }

}