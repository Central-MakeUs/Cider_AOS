package com.cider.cider.domain.repository

interface LoginRepository {
    suspend fun postLoginFirst(header: String): Any
    suspend fun postLogin()
    suspend fun getRandomNickName(): String
    suspend fun getNickNameExist(nickname: String): Boolean
}