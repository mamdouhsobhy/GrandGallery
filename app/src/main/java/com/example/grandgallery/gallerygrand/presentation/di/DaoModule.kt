package com.example.grandgallery.gallerygrand.presentation.di

import com.example.grandgallery.gallerygrand.data.repositoryImp.RoomRepositoryLocalImp
import com.example.grandgallery.gallerygrand.domain.repository.RoomRepositoryLocal
import com.example.grandgallery.room.RoomDB
import com.example.grandgallery.room.RoomDao
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideDao(roomDB: RoomDB): RoomDao = roomDB.redDao()!!


    @Provides
    @Singleton
    fun provideRoomRepositoryLocal(roomDao: RoomDao): RoomRepositoryLocal =
        RoomRepositoryLocalImp(roomDao)


}