package com.example.grandgallery.homedonor.data.repositoryimb

import com.example.grandgallery.homedonor.data.datasource.DonorService
import com.example.grand.homedonor.data.responseremote.addcomment.ModelAddCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.addlike.ModelAddLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.deletecomment.ModelDeleteCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.deletelike.ModelDeleteLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.getposts.ModelGetAdminPostResponseRemote
import com.example.grandgallery.homedonor.domain.repository.DonorRepository
import com.example.grand.homedonor.domain.requestremote.*
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.homedonor.data.responseremote.addpost.ModelAddPostResponseRempte
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap

import javax.inject.Inject


class DonorRepositoryImpl @Inject constructor(private val donorService: DonorService) :
    DonorRepository {
    override suspend fun getPosts(userID: String): Flow<BaseResult<WrappedResponse<List<ModelGetAdminPostResponseRemote>>>> {

        return flow {
            val response = donorService.getPosts(userID)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<List<ModelGetAdminPostResponseRemote>>>() {}.type
                val err: WrappedResponse<List<ModelGetAdminPostResponseRemote>> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }

    }

    override suspend fun getUsers(userID: String): Flow<BaseResult<WrappedResponse<List<ModelGetUsersResponseRemote>>>> {
        return flow {
            val response = donorService.getUseres(userID)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<List<ModelGetUsersResponseRemote>>>() {}.type
                val err: WrappedResponse<List<ModelGetUsersResponseRemote>> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }
    }

    override suspend fun addLike(modelAddLike: ModelAddLike): Flow<BaseResult<WrappedResponse<ModelAddLikeResponseRemote>>> {

        return flow {
            val response = donorService.addLike(modelAddLike)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<ModelAddLikeResponseRemote>>() {}.type
                val err: WrappedResponse<ModelAddLikeResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }

   }

    override suspend fun deleteLike(id: String): Flow<BaseResult<WrappedResponse<ModelDeleteLikeResponseRemote>>> {
        return flow {
            val response = donorService.deletLike(id)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<ModelDeleteLikeResponseRemote>>() {}.type
                val err: WrappedResponse<ModelDeleteLikeResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }
    }

    override suspend fun deleteComent(id: String): Flow<BaseResult<WrappedResponse<ModelDeleteCommentResponseRemote>>> {
        return flow {
            val response = donorService.deleteComment(id)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<ModelDeleteCommentResponseRemote>>() {}.type
                val err: WrappedResponse<ModelDeleteCommentResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }
    }

    override suspend fun addComment(modelAddComment: ModelAddComment): Flow<BaseResult<WrappedResponse<ModelAddCommentResponseRemote>>> {
        return flow {
            val response = donorService.addComment(modelAddComment)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<ModelAddCommentResponseRemote>>() {}.type
                val err: WrappedResponse<ModelAddCommentResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }
    }

    override suspend fun addPost(data:HashMap<String, RequestBody>, imagePath: MultipartBody.Part): Flow<BaseResult<WrappedResponse<ModelAddPostResponseRempte>>> {
        return flow {
            val response = donorService.addPost(data,imagePath)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object : TypeToken<WrappedResponse<ModelAddPostResponseRempte>>() {}.type
                val err: WrappedResponse<ModelAddPostResponseRempte> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code().toString()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }    }

}