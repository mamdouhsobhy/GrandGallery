package com.example.grandgallery.homedonor.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grand.homedonor.data.responseremote.addcomment.ModelAddCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.addlike.ModelAddLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.deletecomment.ModelDeleteCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.deletelike.ModelDeleteLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.getposts.ModelGetAdminPostResponseRemote
import com.example.grandgallery.homedonor.domain.interactor.HomeDonorUseCase
import com.example.grand.homedonor.domain.requestremote.ModelAddComment
import com.example.grand.homedonor.domain.requestremote.ModelAddLike
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeDonorViewModel @Inject constructor(private val homeDonorUseCase: HomeDonorUseCase) : ViewModel() {
    private val _getPostsState = MutableStateFlow<HomeDonorActivityState>(HomeDonorActivityState.Init)
    val getPostsState: StateFlow<HomeDonorActivityState> get() = _getPostsState

    private val _getUsers = MutableStateFlow<GetUsersActivityState>(GetUsersActivityState.Init)
    val getUsers: StateFlow<GetUsersActivityState> get() = _getUsers

    private val _addCommentState = MutableStateFlow<AddCommentActivityState>(AddCommentActivityState.Init)
    val addCommentState: StateFlow<AddCommentActivityState> get() = _addCommentState

    private val _deletCommentState = MutableStateFlow<DeleteCommentActivityState>(
        DeleteCommentActivityState.Init
    )
    val deletCommentState: StateFlow<DeleteCommentActivityState> get() = _deletCommentState

    private val _addLikedState = MutableStateFlow<AddLikeActivityState>(AddLikeActivityState.Init)
    val addLikedState: StateFlow<AddLikeActivityState> get() = _addLikedState

    private val _deleteLikedState = MutableStateFlow<DeleteLikeActivityState>(
        DeleteLikeActivityState.Init
    )
    val deleteLikedState: StateFlow<DeleteLikeActivityState> get() = _deleteLikedState

    var isScreenLoaded=false
    var isCommentAddedLoaded=false
    var isCommentDeletedLoaded=false
    var isLikedAddedLoaded=false
    var isLikedDeletedLoaded=false

    private fun setLoading(i: Int) {
        if(i==1) {
            _getPostsState.value = HomeDonorActivityState.IsLoading(true)
        }else if(i==2){
            _addCommentState.value = AddCommentActivityState.IsLoading(true)
        }else if(i==3){
            _deletCommentState.value = DeleteCommentActivityState.IsLoading(true)
        }else if(i==4){
            _addLikedState.value = AddLikeActivityState.IsLoading(true)
        }else if(i==5){
            _deleteLikedState.value = DeleteLikeActivityState.IsLoading(true)
        }else{
            _getUsers.value = GetUsersActivityState.IsLoading(true)

        }
    }

    private fun hideLoading(i: Int) {
        if(i==1) {
            _getPostsState.value = HomeDonorActivityState.IsLoading(false)
        }else if(i==2){
            _addCommentState.value = AddCommentActivityState.IsLoading(false)
        }else if(i==3){
            _deletCommentState.value = DeleteCommentActivityState.IsLoading(false)
        }else if(i==4){
            _addLikedState.value = AddLikeActivityState.IsLoading(false)
        }else if(i==5){
            _deleteLikedState.value = DeleteLikeActivityState.IsLoading(false)
        }else{
            _getUsers.value = GetUsersActivityState.IsLoading(false)

        }
    }

    private fun showToast(message: String, isConnectionError: Boolean, i: Int) {
        if(i==1) {
            _getPostsState.value = HomeDonorActivityState.ShowToast(message, isConnectionError)
        }else if(i==2){
            _addCommentState.value = AddCommentActivityState.ShowToast(message, isConnectionError)
        }else if(i==3){
            _deletCommentState.value =
                DeleteCommentActivityState.ShowToast(message, isConnectionError)
        }else if(i==4){
            _addLikedState.value = AddLikeActivityState.ShowToast(message, isConnectionError)
        }else if(i==5){
            _deleteLikedState.value = DeleteLikeActivityState.ShowToast(message, isConnectionError)
        }else{
            _getUsers.value = GetUsersActivityState.ShowToast(message, isConnectionError)

        }

    }


    fun getPosts(userId:String) {
        viewModelScope.launch() {
            homeDonorUseCase.executeGetPosts(userId)
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
                        is BaseResult.ErrorState -> _getPostsState.value =
                            HomeDonorActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _getPostsState.value = it.items?.data?.let { it1 ->
                            HomeDonorActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

    fun getUsers(userId:String) {
        viewModelScope.launch() {
            homeDonorUseCase.executeGetUsers(userId)
                .onStart {
                    setLoading(10)
                }
                .catch { exception ->
                    hideLoading(10)
                    showToast(exception.message.toString(), exception is UnknownHostException,10)
                }
                .collect {
                    hideLoading(10)
                    when (it) {
                        is BaseResult.ErrorState -> _getUsers.value =
                            GetUsersActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _getUsers.value = it.items?.data?.let { it1 ->
                            GetUsersActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }


    fun addComment(modelAddComment: ModelAddComment) {
        viewModelScope.launch() {
            homeDonorUseCase.executeAddComment(modelAddComment)
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
                        is BaseResult.ErrorState -> _addCommentState.value =
                            AddCommentActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _addCommentState.value = it.items?.data?.let { it1 ->
                            AddCommentActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

    fun deleteComment(id:String) {
        viewModelScope.launch() {
            homeDonorUseCase.executeDeleteComment(id)
                .onStart {
                    setLoading(3)
                }
                .catch { exception ->
                    hideLoading(3)
                    showToast(exception.message.toString(), exception is UnknownHostException, 3)
                }
                .collect {
                    hideLoading(3)
                    when (it) {
                        is BaseResult.ErrorState -> _deletCommentState.value =
                            DeleteCommentActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _deletCommentState.value = it.items?.data?.let { it1 ->
                            DeleteCommentActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

    fun addLike(modelAddLike: ModelAddLike) {
        viewModelScope.launch() {
            homeDonorUseCase.executeAddLike(modelAddLike)
                .onStart {
                    setLoading(4)
                }
                .catch { exception ->
                    hideLoading(4)
                    showToast(exception.message.toString(), exception is UnknownHostException, 4)
                }
                .collect {
                    hideLoading(4)
                    when (it) {
                        is BaseResult.ErrorState -> _addLikedState.value =
                            AddLikeActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _addLikedState.value = it.items?.data?.let { it1 ->
                            AddLikeActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

    fun deleteLike(likeId:String) {
        viewModelScope.launch() {
            homeDonorUseCase.executeDeleteLike(likeId)
                .onStart {
                    setLoading(5)
                }
                .catch { exception ->
                    hideLoading(5)
                    showToast(exception.message.toString(), exception is UnknownHostException, 5)
                }
                .collect {
                    hideLoading(5)
                    when (it) {
                        is BaseResult.ErrorState -> _deleteLikedState.value =
                            DeleteLikeActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _deleteLikedState.value = it.items?.data?.let { it1 ->
                            DeleteLikeActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }


}


sealed class HomeDonorActivityState {
    object Init : HomeDonorActivityState()
    data class IsLoading(val isLoading: Boolean) : HomeDonorActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : HomeDonorActivityState()
    data class Success(val modelGetPosts: List<ModelGetAdminPostResponseRemote>) :
        HomeDonorActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : HomeDonorActivityState()
}

sealed class GetUsersActivityState {
    object Init : GetUsersActivityState()
    data class IsLoading(val isLoading: Boolean) : GetUsersActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : GetUsersActivityState()
    data class Success(val modelGetPosts: List<ModelGetUsersResponseRemote>) :
        GetUsersActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : GetUsersActivityState()
}

sealed class AddCommentActivityState {
    object Init : AddCommentActivityState()
    data class IsLoading(val isLoading: Boolean) : AddCommentActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : AddCommentActivityState()
    data class Success(val modelAddComment: ModelAddCommentResponseRemote) :
        AddCommentActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : AddCommentActivityState()
}

sealed class DeleteCommentActivityState {
    object Init : DeleteCommentActivityState()
    data class IsLoading(val isLoading: Boolean) : DeleteCommentActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : DeleteCommentActivityState()
    data class Success(val modelDeleteComment: ModelDeleteCommentResponseRemote) :
        DeleteCommentActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : DeleteCommentActivityState()
}
sealed class AddLikeActivityState {
    object Init : AddLikeActivityState()
    data class IsLoading(val isLoading: Boolean) : AddLikeActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : AddLikeActivityState()
    data class Success(val modelAddLike: ModelAddLikeResponseRemote) :
        AddLikeActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : AddLikeActivityState()
}

sealed class DeleteLikeActivityState {
    object Init : DeleteLikeActivityState()
    data class IsLoading(val isLoading: Boolean) : DeleteLikeActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : DeleteLikeActivityState()
    data class Success(val modelDeleteLike: ModelDeleteLikeResponseRemote) :
        DeleteLikeActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : DeleteLikeActivityState()
}