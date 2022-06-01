package com.example.grandgallery.gallerygrand.presentation.fragment.fragmentshow

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.grandgallery.BuildConfig
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.databinding.FragmentShowPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class ShowPhotoFragment : BaseFragmentBinding<FragmentShowPhotoBinding>() {

    private val args: ShowPhotoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImage()
        addListenerOnView()
    }

    private fun addListenerOnView() {
        binding.btnShare.setOnClickListener {
            shareImage((args.url).toUri())
        }
    }

    private fun setImage() {
        Glide.with(this).load(args.url + ".png").into(binding.imgShow)
    }

    private fun shareImage(url: Uri?) {
        val bmpUri: Uri = getLocalBitmapUri(binding.imgShow)!!
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
        shareIntent.type = "image/*"
        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }

    fun getLocalBitmapUri(imageView: ImageView): Uri? {
        val drawable: Drawable = imageView.getDrawable()
        var bmp: Bitmap? = null
        bmp = if (drawable is BitmapDrawable) {
            (imageView.getDrawable() as BitmapDrawable).bitmap
        } else {
            return null
        }
        var bmpUri: Uri? = null
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ), "share_image_" + System.currentTimeMillis() + ".png"
            )
            file.getParentFile().mkdirs()
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = FileProvider.getUriForFile(
                requireActivity(), BuildConfig.APPLICATION_ID.toString() + ".provider",
                file
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }
}