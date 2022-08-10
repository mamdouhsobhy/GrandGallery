package com.example.grandgallery.authorize.presentation.di

import com.example.grandgallery.authorize.data.datasource.LoginService
import com.example.grandgallery.authorize.data.repositoryimb.LoginRepositoryImpl
import com.example.grandgallery.authorize.domain.repository.LoginRepository
import com.example.grandgallery.core.data.module.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit) : LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: LoginService) : LoginRepository {
        return LoginRepositoryImpl(loginService)
    }


}