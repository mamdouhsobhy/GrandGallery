package com.example.grandgallery.gallerygrand.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.databinding.FragmentUserPhotosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPhotosFragment : BaseFragmentBinding<FragmentUserPhotosBinding>() {

    private val args: UserPhotosFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}