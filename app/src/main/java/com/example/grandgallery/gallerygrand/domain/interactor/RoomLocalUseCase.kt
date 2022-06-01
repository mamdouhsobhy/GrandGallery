package com.example.grandgallery.gallerygrand.domain.interactor
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemoteItem
import com.example.grandgallery.gallerygrand.domain.repository.RoomRepositoryLocal
import javax.inject.Inject

class RoomLocalUseCase @Inject constructor(private var repository: RoomRepositoryLocal){

    suspend fun invokeAlbum(): List<ModelGetAlbumsResponseRemoteItem> =repository.getAlbum()

    suspend fun insertAlbum(album: List<ModelGetAlbumsResponseRemoteItem>) {
        repository.insertAlbum(album)
    }

}
