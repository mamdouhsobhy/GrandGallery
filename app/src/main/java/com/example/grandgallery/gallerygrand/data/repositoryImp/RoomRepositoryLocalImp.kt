package com.example.grandgallery.gallerygrand.data.repositoryImp

import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemoteItem
import com.example.grandgallery.gallerygrand.domain.repository.RoomRepositoryLocal
import com.example.grandgallery.room.RoomDao
import javax.inject.Inject


/**
 * This repository is responsible for
 * fetching data from  db
 */
class RoomRepositoryLocalImp @Inject constructor(private val roomDao: RoomDao) :
    RoomRepositoryLocal {


    override suspend fun insertAlbum(album: List<ModelGetAlbumsResponseRemoteItem>) {
        roomDao.insertAlbums(album)
    }

    override suspend fun getAlbum(): List<ModelGetAlbumsResponseRemoteItem> {
        return roomDao.getAlbum()!!
    }
}