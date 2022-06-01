package com.example.grandgallery.gallerygrand.presentation.di

import com.example.grandgallery.core.data.module.NetworkModule
import com.example.grandgallery.gallerygrand.data.datasource.GrandService
import com.example.grandgallery.gallerygrand.data.repositoryImp.GrandRepositoryImpl
import com.example.grandgallery.gallerygrand.domain.repository.GrandRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class GrandModule {

    @Singleton
    @Provides
    fun provideGrandApi(retrofit: Retrofit) : GrandService {
        return retrofit.create(GrandService::class.java)
    }

    @Singleton
    @Provides
    fun provideGrandRepository(grandService: GrandService) : GrandRepository {
        return GrandRepositoryImpl(grandService)
    }


}