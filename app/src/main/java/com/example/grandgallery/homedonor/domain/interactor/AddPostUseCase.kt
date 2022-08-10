package com.example.grandgallery.homedonor.domain.interactor

import com.example.grand.homedonor.data.responseremote.addcomment.ModelAddCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.addlike.ModelAddLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.deletecomment.ModelDeleteCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.deletelike.ModelDeleteLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.getposts.ModelGetAdminPostResponseRemote
import com.example.grandgallery.homedonor.domain.repository.DonorRepository
import com.example.grand.homedonor.domain.requestremote.ModelAddComment
import com.example.grand.homedonor.domain.requestremote.ModelAddLike
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.homedonor.data.responseremote.addpost.ModelAddPostResponseRempte
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap
import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val donorRepository: DonorRepository) {

    suspend fun executeAddPosts(data:HashMap<String, RequestBody>, imagePath: MultipartBody.Part): Flow<BaseResult<WrappedResponse<ModelAddPostResponseRempte>>> {
        return donorRepository.addPost(data,imagePath)
    }


}