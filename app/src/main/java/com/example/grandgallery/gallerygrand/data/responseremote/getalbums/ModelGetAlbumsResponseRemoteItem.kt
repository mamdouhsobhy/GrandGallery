package com.example.grandgallery.gallerygrand.data.responseremote.getalbums

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_tab")
data class ModelGetAlbumsResponseRemoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "userId")
    val userId: Int?
)