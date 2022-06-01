package com.example.grandgallery.gallerygrand.data.datasource

import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemote
import retrofit2.Response
import retrofit2.http.*
import java.util.HashMap

interface GrandService {

    @GET("users")
    suspend fun getUsers(): Response<ModelGetUsersResponseRemote>


    @GET("albums")
    suspend fun getUserAlbums(
        @Query("userId") userId: Int
    ): Response<ModelGetAlbumsResponseRemote>

}
