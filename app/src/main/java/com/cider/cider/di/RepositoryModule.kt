package com.cider.cider.di

import com.cider.cider.data.remote.api.ChallengeApi
import com.cider.cider.data.remote.datasource.LoginRepositoryImpl
import com.cider.cider.data.remote.api.LoginApi
import com.cider.cider.data.remote.datasource.ChallengeRepositoryImpl
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideRegisterRepository (
        loginApi: LoginApi
    ): LoginRepository = LoginRepositoryImpl(loginApi)

    @Provides
    fun provideChallengeRepository (
        challengeApi: ChallengeApi
    ): ChallengeRepository = ChallengeRepositoryImpl(challengeApi)
}