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
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeDonorUseCase @Inject constructor(private val donorRepository: DonorRepository) {

    suspend fun executeGetPosts(userID:String): Flow<BaseResult<WrappedResponse<List<ModelGetAdminPostResponseRemote>>>> {
        return donorRepository.getPosts(userID)
    }

    suspend fun executeGetUsers(userID:String): Flow<BaseResult<WrappedResponse<List<ModelGetUsersResponseRemote>>>> {
        return donorRepository.getUsers(userID)
    }

    suspend fun executeAddLike(modelAddLike: ModelAddLike): Flow<BaseResult<WrappedResponse<ModelAddLikeResponseRemote>>> {
        return donorRepository.addLike(modelAddLike)
    }

    suspend fun executeDeleteLike(id:String): Flow<BaseResult<WrappedResponse<ModelDeleteLikeResponseRemote>>> {
        return donorRepository.deleteLike(id)
    }

    suspend fun executeDeleteComment(id:String): Flow<BaseResult<WrappedResponse<ModelDeleteCommentResponseRemote>>> {
        return donorRepository.deleteComent(id)
    }

    suspend fun executeAddComment(modelAddComment: ModelAddComment): Flow<BaseResult<WrappedResponse<ModelAddCommentResponseRemote>>> {
        return donorRepository.addComment(modelAddComment)
    }

}