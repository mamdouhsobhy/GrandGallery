package com.example.grandgallery.gallerygrand.data.repositoryImp

import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.gallerygrand.data.datasource.GrandService
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getphotos.ModelGetPhotosResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemote
import com.example.grandgallery.gallerygrand.domain.repository.GrandRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GrandRepositoryImpl @Inject constructor(private val grandService: GrandService) :
    GrandRepository {
    override suspend fun getUsers(): Flow<BaseResult<ModelGetUsersResponseRemote>> {

        return flow {
            val response = grandService.getUsers()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<ModelGetUsersResponseRemote>() {}.type
                val err: WrappedResponse<ModelGetUsersResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }


    }

    override suspend fun getUserAlbums(userID: Int): Flow<BaseResult<ModelGetAlbumsResponseRemote>> {
        return flow {
            val response = grandService.getUserAlbums(userID)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<ModelGetUsersResponseRemote>() {}.type
                val err: WrappedResponse<ModelGetUsersResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }


    }

    override suspend fun getUserPhotos(albumID: String): Flow<BaseResult<ModelGetPhotosResponseRemote>> {
        return flow {
            val response = grandService.getUserPhoto(albumID)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<ModelGetPhotosResponseRemote>() {}.type
                val err: WrappedResponse<ModelGetPhotosResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }
    }

}