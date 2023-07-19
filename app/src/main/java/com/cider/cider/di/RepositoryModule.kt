package com.cider.cider.di

import com.cider.cider.data.remote.api.ChallengeApi
import com.cider.cider.data.remote.datasource.RegisterRepositoryImpl
import com.cider.cider.data.remote.api.RegisterApi
import com.cider.cider.data.remote.datasource.ChallengeRepositoryImpl
import com.cider.cider.domain.repository.ChallengeReposiory
import com.cider.cider.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideRegisterRepository (
        registerApi: RegisterApi
    ): RegisterRepository = RegisterRepositoryImpl(registerApi)

    @Provides
    fun provideChallengeRepository (
        challengeApi: ChallengeApi
    ): ChallengeReposiory = ChallengeRepositoryImpl(challengeApi)
}