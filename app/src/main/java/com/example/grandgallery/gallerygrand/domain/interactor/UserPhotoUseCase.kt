package com.example.grandgallery.gallerygrand.domain.interactor
import com.example.grandgallery.core.presentation.base.BaseResult
import com.example.grandgallery.gallerygrand.data.responseremote.getphotos.ModelGetPhotosResponseRemote
import com.example.grandgallery.gallerygrand.domain.repository.GrandRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPhotoUseCase @Inject constructor(private val grandRepository: GrandRepository) {
    suspend fun executeGetPhotos(albumID: String): Flow<BaseResult<ModelGetPhotosResponseRemote>> {
        return grandRepository.getUserPhotos(albumID)
    }

}