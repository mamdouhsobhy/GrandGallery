package com.example.grandgallery.gallerygrand.presentation

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grandgallery.R
import com.example.grandgallery.core.presentation.base.BaseActivity
import com.example.grandgallery.core.presentation.utilities.ConnectionReceiver
import com.example.grandgallery.databinding.ActivityGalleryGrandBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryGrandActivity : BaseActivity<ActivityGalleryGrandBinding>(),
    ConnectionReceiver.ReceiverListener {
    var isConnectedForMenu = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkConnection()
    }

    private fun checkConnection() {

        // initialize intent filter
        val intentFilter = IntentFilter()

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE")

        // register receiver
        registerReceiver(ConnectionReceiver(), intentFilter)

        // Initialize listener
        ConnectionReceiver.Listener = this

        // Initialize connectivity manager
        val manager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Initialize network info
        val networkInfo = manager.activeNetworkInfo

        // get connection status
        val isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting
        isConnectedForMenu = isConnected

    }


    override fun onResume() {
        super.onResume()
        // call method
        checkConnection()
    }

    override fun onPause() {
        super.onPause()
        // call method
        checkConnection()
    }

    override fun onNetworkChange(isConnected: Boolean) {
    }
}