package com.example.grandgallery.gallerygrand.presentation.fragment.fragmentphoto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.gallerygrand.data.responseremote.getphotos.ModelGetPhotosResponseRemote
import com.example.grandgallery.gallerygrand.domain.interactor.UserPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class UserPhotosViewModel
@Inject constructor(private val userPhotoUseCase: UserPhotoUseCase) : ViewModel() {
    private val _userPhotoState =
        MutableStateFlow<UserPhotoState>(UserPhotoState.Init)
    val userPhotoState: StateFlow<UserPhotoState> get() = _userPhotoState

    var isScreenLoaded = false

    private fun setLoading() {

        _userPhotoState.value = UserPhotoState.IsLoading(true)

    }

    private fun hideLoading() {

        _userPhotoState.value = UserPhotoState.IsLoading(false)

    }

    private fun showToast(message: String, isConnectionError: Boolean) {

        _userPhotoState.value = UserPhotoState.ShowToast(message, isConnectionError)

    }


    fun getAlbumUser(albumID: String) {

        viewModelScope.launch {
            userPhotoUseCase.executeGetPhotos(albumID)
                .onStart {

                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString(), exception is UnknownHostException)
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.ErrorState -> _userPhotoState.value =
                            UserPhotoState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> {

                            _userPhotoState.value = it.items?.let { it ->

                                UserPhotoState.Success(
                                    it
                                )
                            }!!
                        }
                    }
                }
        }

    }



    sealed class UserPhotoState {
        object Init : UserPhotoState()
        data class IsLoading(val isLoading: Boolean) : UserPhotoState()
        data class ShowToast(val message: String, val isConnectionError: Boolean) :
            UserPhotoState()

        data class Success(val modelGetUserPhoto: ModelGetPhotosResponseRemote) :
            UserPhotoState()

        data class ErrorLogin(val errorCode: Int, val errorMessage: String) : UserPhotoState()
    }
}