package com.example.grandgallery.sidemenu.talabat

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.core.presentation.extensions.showGenericAlertDialog
import com.example.grandgallery.core.presentation.extensions.showToast
import com.example.grandgallery.databinding.FragmentTalabatBinding
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote
import com.example.grandgallery.homedonor.presentation.home.viewmodel.GetUsersActivityState
import com.example.grandgallery.homedonor.presentation.home.viewmodel.HomeDonorViewModel
import com.example.grandgallery.sidemenu.talabat.adapter.TalabatVolunterAdapter

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class FragmentTalabat : BaseFragmentBinding<FragmentTalabatBinding>() {
    private val args: FragmentTalabatArgs by navArgs()

    private val adapter: TalabatVolunterAdapter by lazy {
        TalabatVolunterAdapter()
    }

    val viewModel: HomeDonorViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeDonorViewModel::class.java]
    }

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUsers(args.type)

        observeStateFlow()
        setUpNotify()


    }

    private fun setUpNotify() {
        binding.rvTalabat.adapter = adapter
    }


    private fun observeStateFlow() {
        viewModel.getUsers
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun handleStateChange(state: GetUsersActivityState) {
        when (state) {
            is GetUsersActivityState.Init -> Unit
            is GetUsersActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is GetUsersActivityState.Success -> handleSuccess(state.modelGetPosts)
            is GetUsersActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is GetUsersActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }


    private fun handleLoading(isLoading: Boolean) {
       if(isLoading){
           showLoading()
       }else{
           dismissLoading()
       }
    }

    private fun handleSuccess(posts: List<ModelGetUsersResponseRemote>) {
        binding.tvNoDataFound.isVisible=posts.isEmpty()
        adapter.submitList(posts)
        viewModel.isScreenLoaded = true
        binding.swipeToRefresh.isRefreshing = false

    }

    private fun handleErrorLogin(errorCode: String, errorMessage: String) {

        requireActivity().showGenericAlertDialog(errorMessage)
        dismissLoading()


    }


}