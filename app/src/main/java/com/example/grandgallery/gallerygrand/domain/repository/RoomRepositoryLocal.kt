package com.example.grandgallery.gallerygrand.domain.repository

import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemoteItem


interface RoomRepositoryLocal {

    suspend fun insertAlbum(album: List<ModelGetAlbumsResponseRemoteItem>)

    suspend fun getAlbum(): List<ModelGetAlbumsResponseRemoteItem>

}