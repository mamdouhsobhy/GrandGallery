package com.example.grandgallery.gallerygrand.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.grandgallery.room.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class DataBaseModule {
     //var converter:Converter= Converter()
    @Provides
    @Singleton
    fun provideDB(application: Application): RoomDB = Room.databaseBuilder(application, RoomDB::class.java, "album_tab")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


}