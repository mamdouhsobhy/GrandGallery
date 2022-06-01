package com.example.grandgallery.gallerygrand.domain.repository

import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemote
import kotlinx.coroutines.flow.Flow

interface GrandRepository {
    suspend fun getUsers(): Flow<BaseResult<ModelGetUsersResponseRemote>>
    suspend fun getUserAlbums(userID: Int): Flow<BaseResult<ModelGetAlbumsResponseRemote>>

}