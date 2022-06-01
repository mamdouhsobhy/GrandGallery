package com.example.grandgallery.gallerygrand.presentation.fragment.fragmentphoto

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.grandgallery.R
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.core.presentation.extensions.showGenericAlertDialog
import com.example.grandgallery.core.presentation.extensions.showToast
import com.example.grandgallery.core.presentation.utilities.Nav
import com.example.grandgallery.databinding.FragmentUserPhotosBinding
import com.example.grandgallery.gallerygrand.data.responseremote.getphotos.ModelGetPhotosResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getphotos.ModelGetPhotosResponseRemoteItem
import com.example.grandgallery.gallerygrand.presentation.fragment.fragmentphoto.adapter.UserPhotosAdapter
import com.example.grandgallery.gallerygrand.presentation.fragment.fragmentphoto.viewmodel.UserPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UserPhotosFragment : BaseFragmentBinding<FragmentUserPhotosBinding>() {

    private val args: UserPhotosFragmentArgs by navArgs()

    private val viewModel by viewModels<UserPhotosViewModel>()

    lateinit var model:ModelGetPhotosResponseRemote
    private val userPhotoAdapter: UserPhotosAdapter by lazy {
        UserPhotosAdapter(itemSelectedForActions = {
            val bundle = Bundle().apply {
                putString(Nav.Albums.URL, it.url.toString())
            }
            findNavController().navigate(R.id.showPhotoFragment, bundle)
        }) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isScreenLoaded.not()) {
            viewModel.getAlbumUser(args.albumid)
        }
        txt_watcher()
        addListenersOnViews()
        observeStateFlow()
        setUpUserAlbumsPhotos()

    }

    private fun setUpUserAlbumsPhotos() {
        binding.rvUserPhotos.adapter = userPhotoAdapter
    }

    private fun observeStateFlow() {
        viewModel.userPhotoState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateGetUserChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
       
    }

    private fun handleStateGetUserChange(state: UserPhotosViewModel.UserPhotoState) {
        when (state) {
            is UserPhotosViewModel.UserPhotoState.Init -> Unit
            is UserPhotosViewModel.UserPhotoState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is UserPhotosViewModel.UserPhotoState.Success -> handleSuccess(state.modelGetUserPhoto)
            is UserPhotosViewModel.UserPhotoState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is UserPhotosViewModel.UserPhotoState.IsLoading -> handleLoading(state.isLoading)
        }
    }


    private fun handleErrorLogin(errorCode: Int, errorMessage: String) {
        requireActivity().showGenericAlertDialog(errorMessage)
    }

    private fun handleLoading(isLoading: Boolean) {

        binding.swipeToRefresh.isRefreshing = isLoading

    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccess(modelGetUser: ModelGetPhotosResponseRemote) {
        model=modelGetUser
        userPhotoAdapter.submitList(modelGetUser)

    }

  
    private fun addListenersOnViews() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getAlbumUser(args.albumid)
        }

    }

    private fun txt_watcher() {
        binding.edSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    var listSubData = ArrayList<ModelGetPhotosResponseRemoteItem>()
                    for (item in model) {
                        if (item.title?.contains(s) == true) {
                            listSubData.add(item)
                            userPhotoAdapter.submitList(listSubData)
                            userPhotoAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

}