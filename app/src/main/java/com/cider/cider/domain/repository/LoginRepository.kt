package com.cider.cider.domain.repository

import com.cider.cider.data.remote.model.ResponseMe

interface LoginRepository {
    suspend fun postLoginFirst(header: String): Any
    suspend fun postLogin()
    suspend fun getRandomNickName(): String
    suspend fun getNickNameExist(nickname: String): Boolean

    suspend fun getLoginMe(): Boolean
    suspend fun getMe(): ResponseMe?
    suspend fun postLogout()
}