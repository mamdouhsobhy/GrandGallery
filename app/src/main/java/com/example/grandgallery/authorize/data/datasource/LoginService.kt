package com.example.grandgallery.authorize.data.datasource

import com.example.grand.authorize.data.responseremote.ModelLoginResponseRemote
import com.example.grand.authorize.domain.requestremote.ModelLogin
import com.example.grandgallery.core.data.utils.WrappedResponse
import retrofit2.Response
import retrofit2.http.*

interface LoginService {

    @POST("Login")
    suspend fun login(@Body modelLogin: ModelLogin): Response<WrappedResponse<ModelLoginResponseRemote>>

}