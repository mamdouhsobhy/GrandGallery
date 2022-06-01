package com.example.grandgallery.gallerygrand.data.responseremote.getusers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_tab")
data class ModelGetUsersResponseRemoteItem(
    val address: Address?,
    //val company: Company?,
    val email: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    val phone: String?,
    val username: String?,
    val website: String?
)