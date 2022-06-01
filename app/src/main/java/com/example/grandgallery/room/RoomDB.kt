package com.example.grandgallery.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem

@Database(entities = [ModelGetAlbumsResponseRemoteItem::class], version =1 ,exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun redDao(): RoomDao?
}