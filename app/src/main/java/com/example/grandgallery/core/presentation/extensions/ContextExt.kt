package com.example.grandgallery.core.presentation.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.grandgallery.R

fun Context.showToast(message: String, connectionError: Boolean=false){
    if (connectionError){

        Toast.makeText(this, getString(R.string.check_internet_connections), Toast.LENGTH_SHORT).show()
    }else {
        Log.d("errror", message)
        if (message.isNotEmpty()) {
            Log.d("errror", message)
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("ok"){ dialog, _ ->
             dialog.dismiss()
        }
    }.show()
}