package com.example.grandgallery.authorize.data.repositoryimb
import com.example.grandgallery.authorize.data.datasource.LoginService
import com.example.grand.authorize.data.responseremote.ModelLoginResponseRemote
import com.example.grandgallery.authorize.domain.repository.LoginRepository
import com.example.grand.authorize.domain.requestremote.ModelLogin
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(private val loginService: LoginService) :
    LoginRepository {

    override suspend fun login(modelLogin: ModelLogin): Flow<BaseResult<WrappedResponse<ModelLoginResponseRemote>>> {
        return flow {
            val response = loginService.login(modelLogin)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<ModelLoginResponseRemote>>() {}.type
                val err: WrappedResponse<ModelLoginResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }
    }


}