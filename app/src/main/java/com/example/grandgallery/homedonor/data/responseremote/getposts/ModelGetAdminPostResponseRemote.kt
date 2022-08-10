package com.example.grand.homedonor.data.responseremote.getposts

data class ModelGetAdminPostResponseRemote(
    val comments: List<Comment>?,
    val content: String?,
    val currentUserLikes: Boolean?,
    val date: String?,
    val id: Int?,
    val image: Any?,
    val imageName: String?,
    val likes: List<Like>?
)
data class Comment(
    val applicationUserId: String?,
    val content: String?,
    val date: String?,
    val id: Int?,
    val postId: Int?,
    val userName: String?
)
data class Like(
    val applicationUserId: String?,
    val date: String?,
    val id: Int?,
    val postId: Int?,
    val userName: String?
)