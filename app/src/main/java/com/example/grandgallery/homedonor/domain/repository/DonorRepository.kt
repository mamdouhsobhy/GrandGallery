package com.example.grandgallery.homedonor.domain.repository

import com.example.grand.homedonor.data.responseremote.addcomment.ModelAddCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.addlike.ModelAddLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.deletecomment.ModelDeleteCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.deletelike.ModelDeleteLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.getposts.ModelGetAdminPostResponseRemote
import com.example.grand.homedonor.domain.requestremote.*
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.homedonor.data.responseremote.addpost.ModelAddPostResponseRempte
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap

interface DonorRepository {

    suspend fun getPosts(userID:String) : Flow<BaseResult<WrappedResponse<
            List<ModelGetAdminPostResponseRemote>>>>

    suspend fun getUsers(userID:String) : Flow<BaseResult<WrappedResponse<
            List<ModelGetUsersResponseRemote>>>>

    suspend fun addLike(modelAddLike: ModelAddLike) : Flow<BaseResult<WrappedResponse<
            ModelAddLikeResponseRemote>>>

    suspend fun deleteLike(id: String) : Flow<BaseResult<WrappedResponse<
            ModelDeleteLikeResponseRemote>>>

    suspend fun deleteComent(id: String) : Flow<BaseResult<WrappedResponse<
            ModelDeleteCommentResponseRemote>>>

    suspend fun addComment(modelAddComment: ModelAddComment) : Flow<BaseResult<WrappedResponse<
            ModelAddCommentResponseRemote>>>

    suspend fun addPost(data:HashMap<String, RequestBody>, imagePath: MultipartBody.Part) : Flow<BaseResult<WrappedResponse<
            ModelAddPostResponseRempte>>>


}