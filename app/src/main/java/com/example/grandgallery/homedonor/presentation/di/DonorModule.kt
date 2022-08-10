package com.example.grandgallery.homedonor.presentation.di

import com.example.grandgallery.core.data.module.NetworkModule
import com.example.grandgallery.homedonor.data.datasource.DonorService
import com.example.grandgallery.homedonor.data.repositoryimb.DonorRepositoryImpl
import com.example.grandgallery.homedonor.domain.repository.DonorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class DonorModule {

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit) : DonorService {
        return retrofit.create(DonorService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(donorService: DonorService) : DonorRepository {
        return DonorRepositoryImpl(donorService)
    }


}