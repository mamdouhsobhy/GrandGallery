package com.example.grandgallery.sidemenu.addpost.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.homedonor.data.responseremote.addpost.ModelAddPostResponseRempte
import com.example.grandgallery.homedonor.domain.interactor.AddPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.net.UnknownHostException
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(private val addPostUseCase: AddPostUseCase) : ViewModel() {
    private val _addPostsState = MutableStateFlow<AddPostActivityState>(AddPostActivityState.Init)
    val addPostsState: StateFlow<AddPostActivityState> get() = _addPostsState

   
    var isScreenLoaded=false
    
    private fun setLoading(i: Int) {
        if(i==1) {
            _addPostsState.value = AddPostActivityState.IsLoading(true)
        }
    }

    private fun hideLoading(i: Int) {
        if(i==1) {
            _addPostsState.value = AddPostActivityState.IsLoading(false)
        }
    }

    private fun showToast(message: String, isConnectionError: Boolean, i: Int) {
        if(i==1) {
            _addPostsState.value = AddPostActivityState.ShowToast(message, isConnectionError)
        }
    }


    fun getPosts(data:HashMap<String, RequestBody>, imagePath: MultipartBody.Part) {
        viewModelScope.launch() {
            addPostUseCase.executeAddPosts(data,imagePath)
                .onStart {
                    setLoading(1)
                }
                .catch { exception ->
                    hideLoading(1)
                    showToast(exception.message.toString(), exception is UnknownHostException,1)
                }
                .collect {
                    hideLoading(1)
                    when (it) {
                        is BaseResult.ErrorState -> _addPostsState.value =
                            AddPostActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _addPostsState.value = it.items?.data?.let { it1 ->
                            AddPostActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

   
}


sealed class AddPostActivityState {
    object Init : AddPostActivityState()
    data class IsLoading(val isLoading: Boolean) : AddPostActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : AddPostActivityState()
    data class Success(val modelGetPosts: ModelAddPostResponseRempte) :
        AddPostActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : AddPostActivityState()
}



