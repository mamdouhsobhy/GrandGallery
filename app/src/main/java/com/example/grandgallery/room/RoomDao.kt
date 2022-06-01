package com.example.grandgallery.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemoteItem

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlbums(album: List<ModelGetAlbumsResponseRemoteItem>)

    @Query("select * from album_tab")
    fun getAlbum(): List<ModelGetAlbumsResponseRemoteItem>?

}