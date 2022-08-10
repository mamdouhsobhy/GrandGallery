package com.example.grandgallery.homedonor.data.datasource

import com.example.grand.homedonor.data.responseremote.addcomment.ModelAddCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.addlike.ModelAddLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.deletecomment.ModelDeleteCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.deletelike.ModelDeleteLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.getposts.ModelGetAdminPostResponseRemote
import com.example.grand.homedonor.domain.requestremote.*
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.homedonor.data.responseremote.addpost.ModelAddPostResponseRempte
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.util.HashMap

interface DonorService {


    @GET("GetPost/{UserId}")
    suspend fun getPosts(@Path("UserId") UserId: String?): Response<WrappedResponse<
            List<ModelGetAdminPostResponseRemote>>>

    @POST("AddLike")
    suspend fun addLike(@Body modelAddLike: ModelAddLike): Response<WrappedResponse<
            ModelAddLikeResponseRemote>>


    @POST("AddComment")
    suspend fun addComment(@Body modelAddComment: ModelAddComment): Response<WrappedResponse<
            ModelAddCommentResponseRemote>>


    @DELETE("DeleteLike/{id}")
    suspend fun deletLike(@Path("id") id: String): Response<WrappedResponse<
            ModelDeleteLikeResponseRemote>>

    @DELETE("DeleteComment/{id}")
    suspend fun deleteComment(@Path("id") id: String): Response<WrappedResponse<
            ModelDeleteCommentResponseRemote>>

    @GET("GetUsers/{id}")
    suspend fun getUseres(@Path("id") UserId: String?): Response<WrappedResponse<
            List<ModelGetUsersResponseRemote>>>

    @Multipart
    @POST("AddPost")
    suspend fun addPost(@PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
                        @Part Image: MultipartBody.Part ): Response<WrappedResponse<
            ModelAddPostResponseRempte>>


}