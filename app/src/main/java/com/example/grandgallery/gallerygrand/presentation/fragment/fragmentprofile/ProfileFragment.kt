package com.example.grandgallery.gallerygrand.presentation.fragment.fragmentprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.grandgallery.R
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.core.presentation.extensions.showGenericAlertDialog
import com.example.grandgallery.core.presentation.extensions.showToast
import com.example.grandgallery.core.presentation.utilities.Nav
import com.example.grandgallery.databinding.FragmentProfileBinding
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemoteItem
import com.example.grandgallery.gallerygrand.presentation.GalleryGrandActivity
import com.example.grandgallery.gallerygrand.presentation.fragment.fragmentprofile.adapter.UserAlbumsAdapter
import com.example.grandgallery.gallerygrand.presentation.fragment.fragmentprofile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragmentBinding<FragmentProfileBinding>() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var address = ""
    private val viewModel by viewModels<ProfileViewModel>()
    private val userAlbumsAdapter: UserAlbumsAdapter by lazy {
        UserAlbumsAdapter(itemSelectedForActions = {
            val bundle = Bundle().apply {
                putString(Nav.Albums.ALBUM_ID, it.id.toString())
            }
            findNavController().navigate(R.id.userPhotosFragment, bundle)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isScreenLoaded.not()) {
            if ((requireActivity() as GalleryGrandActivity).isConnectedForMenu) {
                viewModel.getUsers()
            } else {
                getLocalUser()
                lifecycleScope.launchWhenStarted {
                    viewModel.getAlbumLocal()

                }
            }

        }

        addListenersOnViews()
        observeStateFlow()
        setUpUserAlbums()

    }


    private fun setUpUserAlbums() {
        binding.rvAlbums.adapter = userAlbumsAdapter
    }

    private fun observeStateFlow() {
        viewModel.profileState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateGetUserChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.albumState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateGetUserAlbumChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateGetUserChange(state: ProfileViewModel.ProfileGetUserState) {
        when (state) {
            is ProfileViewModel.ProfileGetUserState.Init -> Unit
            is ProfileViewModel.ProfileGetUserState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is ProfileViewModel.ProfileGetUserState.Success -> handleSuccess(state.modelGetUser)

            is ProfileViewModel.ProfileGetUserState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is ProfileViewModel.ProfileGetUserState.IsLoading -> handleLoading(state.isLoading)
        }
    }


    private fun handleErrorLogin(errorCode: Int, errorMessage: String) {
        requireActivity().showGenericAlertDialog(errorMessage)
    }

    private fun handleLoading(isLoading: Boolean) {

        binding.swipeToRefresh.isRefreshing = isLoading

    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccess(modelGetUser: ModelGetUsersResponseRemote?) {

        binding.tvUserName.text =
            getString(R.string.username) + " " + modelGetUser?.get(0)?.username
        binding.tvUserAddress.text = getString(R.string.user_address) + " /" +
                modelGetUser?.get(0)?.address?.city + " /" +
                modelGetUser?.get(0)?.address?.street + " /" +
                modelGetUser?.get(0)?.address?.zipcode
        saveLocalUSer(modelGetUser)

        modelGetUser?.get(0)?.id?.let { viewModel.getAlbumUser(it) }

    }

    private fun handleStateGetUserAlbumChange(state: ProfileViewModel.ProfileAlbumUserState) {
        when (state) {
            is ProfileViewModel.ProfileAlbumUserState.Init -> Unit
            is ProfileViewModel.ProfileAlbumUserState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is ProfileViewModel.ProfileAlbumUserState.Success -> handleSuccessGetAlbums(state.modelGetUserAlbum)
            is ProfileViewModel.ProfileAlbumUserState.Successlocal -> handleSuccessLocalAlbums(state.model)
            is ProfileViewModel.ProfileAlbumUserState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is ProfileViewModel.ProfileAlbumUserState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleSuccessLocalAlbums(model: List<ModelGetAlbumsResponseRemoteItem>) {

        userAlbumsAdapter.submitList(model)

    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccessGetAlbums(modelGetUser: ModelGetAlbumsResponseRemote) {

        userAlbumsAdapter.submitList(modelGetUser)
        lifecycleScope.launch {
            viewModel.insertAlbum(modelGetUser)
        }
    }

    private fun addListenersOnViews() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getUsers()
        }
    }

    private fun saveLocalUSer(modelGetUser: ModelGetUsersResponseRemote?) {
        address = getString(R.string.user_address) + " /" +
                modelGetUser?.get(0)?.address?.city + " /" +
                modelGetUser?.get(0)?.address?.street + " /" +
                modelGetUser?.get(0)?.address?.zipcode
        modelGetUser?.get(0)?.username?.let { sharedPrefs.saveUsername(it) }
        sharedPrefs.saveAddress(address)
    }

    @SuppressLint("SetTextI18n")
    private fun getLocalUser() {
        binding.tvUserName.text =getString(R.string.username)+sharedPrefs.getUsername()
        binding.tvUserAddress.text = sharedPrefs.getAddress()
    }
}
