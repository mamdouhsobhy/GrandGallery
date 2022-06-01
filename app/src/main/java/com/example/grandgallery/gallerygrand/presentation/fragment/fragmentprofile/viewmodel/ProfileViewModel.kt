package com.example.grandgallery.gallerygrand.presentation.fragment.fragmentprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemote
import com.example.grandgallery.gallerygrand.domain.interactor.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(private val profileUseCase: ProfileUseCase) : ViewModel() {
    private val _profileState =
        MutableStateFlow<ProfileGetUserState>(ProfileGetUserState.Init)
    val profileState: StateFlow<ProfileGetUserState> get() = _profileState

    private val _albumsState =
        MutableStateFlow<ProfileAlbumUserState>(ProfileAlbumUserState.Init)
    val albumState: StateFlow<ProfileAlbumUserState> get() = _albumsState

    var isScreenLoaded = false

    private fun setLoading(i: Int) {
        if(i==1) {
            _profileState.value = ProfileGetUserState.IsLoading(true)
        }else{
            _albumsState.value = ProfileAlbumUserState.IsLoading(true)
        }
    }

    private fun hideLoading(i: Int) {
        if(i==1) {
            _profileState.value = ProfileGetUserState.IsLoading(false)
        }else{
            _albumsState.value = ProfileAlbumUserState.IsLoading(false)

        }
    }

    private fun showToast(message: String, isConnectionError: Boolean, i: Int) {
        if(i==1) {
            _profileState.value = ProfileGetUserState.ShowToast(message, isConnectionError)
        }else{
            _albumsState.value = ProfileAlbumUserState.ShowToast(message, isConnectionError)

        }
    }

    fun getUsers() {

        viewModelScope.launch {
            profileUseCase.executeGetUsers()
                .onStart {

                    setLoading(1)
                }
                .catch { exception ->
//                    hideLoading(1)
                    showToast(exception.message.toString(), exception is UnknownHostException, 1)
                }
                .collect {
//                    hideLoading(1)
                    when (it) {
                        is BaseResult.ErrorState -> _profileState.value =
                            ProfileGetUserState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> {

                            _profileState.value = it.items?.let { it ->

                                ProfileGetUserState.Success(
                                    it
                                )
                            }!!
                        }
                    }
                }
        }

    }

    fun getAlbumUser(userID:Int) {

        viewModelScope.launch {
            profileUseCase.executeGetAlbums(userID)
                .onStart {

                    setLoading(2)
                }
                .catch { exception ->
                    hideLoading(2)
                    showToast(exception.message.toString(), exception is UnknownHostException, 2)
                }
                .collect {
                    hideLoading(2)
                    when (it) {
                        is BaseResult.ErrorState -> _albumsState.value =
                            ProfileAlbumUserState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> {

                            _albumsState.value = it.items?.let { it ->

                                ProfileAlbumUserState.Success(
                                    it
                                )
                            }!!
                        }
                    }
                }
        }

    }


    sealed class ProfileGetUserState {
        object Init : ProfileGetUserState()
        data class IsLoading(val isLoading: Boolean) : ProfileGetUserState()
        data class ShowToast(val message: String, val isConnectionError: Boolean) :
            ProfileGetUserState()

        data class Success(val modelGetUser: ModelGetUsersResponseRemote?) :
            ProfileGetUserState()

        data class ErrorLogin(val errorCode: Int, val errorMessage: String) : ProfileGetUserState()
    }

    sealed class ProfileAlbumUserState {
        object Init : ProfileAlbumUserState()
        data class IsLoading(val isLoading: Boolean) : ProfileAlbumUserState()
        data class ShowToast(val message: String, val isConnectionError: Boolean) :
            ProfileAlbumUserState()

        data class Success(val modelGetUserAlbum: ModelGetAlbumsResponseRemote) :
            ProfileAlbumUserState()

        data class ErrorLogin(val errorCode: Int, val errorMessage: String) : ProfileAlbumUserState()
    }
}