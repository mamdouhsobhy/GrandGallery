package com.example.grandgallery.sidemenu.selecttype

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.grandgallery.R
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.core.presentation.utilities.Nav
import com.example.grandgallery.databinding.FragmentSelectTypeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSelectType : BaseFragmentBinding<FragmentSelectTypeBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDonor.setOnClickListener {
            val bundle = Bundle().apply {
                putString(Nav.Type.type, "1")
            }
            findNavController().navigate(R.id.fragmentTalabat, bundle)
        }
        binding.btnVolunteer.setOnClickListener {
            val bundle = Bundle().apply {
                putString(Nav.Type.type, "2")
            }
            findNavController().navigate(R.id.fragmentTalabat,bundle)
        }
        binding.btnBenefit.setOnClickListener {
            val bundle = Bundle().apply {
                putString(Nav.Type.type, "3")
            }
            findNavController().navigate(R.id.fragmentTalabat,bundle)
        }
    }
}