package com.cider.cider.di

import com.cider.cider.data.remote.RegisterRepositoryImpl
import com.cider.cider.data.remote.api.RegisterApi
import com.cider.cider.domain.repository.RegisterRepository
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideRegisterRepository (
        registerApi: RegisterApi
    ): RegisterRepository = RegisterRepositoryImpl(registerApi)
}