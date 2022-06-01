package com.example.grandgallery.gallerygrand.domain.interactor
import com.example.grandgallery.core.data.utils.WrappedResponse
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemote
import com.example.grandgallery.gallerygrand.data.responseremote.getusers.ModelGetUsersResponseRemote
import com.example.grandgallery.gallerygrand.domain.repository.GrandRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val grandRepository: GrandRepository) {
    suspend fun executeGetUsers(): Flow<BaseResult<ModelGetUsersResponseRemote>> {
        return grandRepository.getUsers()
    }
    suspend fun executeGetAlbums(userId:Int): Flow<BaseResult<ModelGetAlbumsResponseRemote>> {
        return grandRepository.getUserAlbums(userId)
    }
}