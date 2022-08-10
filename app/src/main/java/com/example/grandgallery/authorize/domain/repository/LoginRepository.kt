package com.example.grandgallery.authorize.domain.repository

import com.example.grand.authorize.data.responseremote.ModelLoginResponseRemote
import com.example.grand.authorize.domain.requestremote.ModelLogin
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(modelLogin: ModelLogin) : Flow<BaseResult<WrappedResponse<
            ModelLoginResponseRemote>>>



}