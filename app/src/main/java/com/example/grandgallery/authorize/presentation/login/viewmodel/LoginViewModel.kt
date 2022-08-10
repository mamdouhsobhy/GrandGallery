package com.example.grandgallery.authorize.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grand.authorize.data.responseremote.ModelLoginResponseRemote
import com.example.grandgallery.authorize.domain.interactor.LoginUseCase
import com.example.grand.authorize.domain.requestremote.ModelLogin
import com.example.grandgallery.core.presentation.base.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginActivityState>(LoginActivityState.Init)
    val loginState: StateFlow<LoginActivityState> get() = _loginState
    var isScreenLoaded=false
    private fun setLoading() {
        _loginState.value = LoginActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        _loginState.value = LoginActivityState.IsLoading(false)
    }

    private fun showToast(message: String, isConnectionError: Boolean) {
        _loginState.value = LoginActivityState.ShowToast(message, isConnectionError)
    }


    fun makeLogin(modelLogin: ModelLogin) {
        viewModelScope.launch() {
            loginUseCase.executeLogin(modelLogin)
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
                        is BaseResult.ErrorState -> _loginState.value =
                            LoginActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _loginState.value = it.items?.data?.let { it1 ->
                            LoginActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

}


sealed class LoginActivityState {
    object Init : LoginActivityState()
    data class IsLoading(val isLoading: Boolean) : LoginActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : LoginActivityState()
    data class Success(val modelLogin: ModelLoginResponseRemote) :
        LoginActivityState()

    data class ErrorLogin(val errorCode: String, val errorMessage: String) : LoginActivityState()
}