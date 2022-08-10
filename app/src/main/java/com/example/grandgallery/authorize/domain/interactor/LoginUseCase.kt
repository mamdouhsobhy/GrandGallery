package com.example.grandgallery.authorize.domain.interactor

import com.example.grand.authorize.data.responseremote.ModelLoginResponseRemote
import com.example.grandgallery.authorize.domain.repository.LoginRepository
import com.example.grand.authorize.domain.requestremote.ModelLogin
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun executeLogin(modelLogin: ModelLogin): Flow<BaseResult<WrappedResponse<ModelLoginResponseRemote>>> {
        return loginRepository.login(modelLogin)
    }

}