package com.cider.cider.domain.repository

interface RegisterRepository {
    suspend fun postLogin(header: String): Any

    suspend fun getRandomNickName(): String
    suspend fun getNickNameExist(nickname: String): Boolean
}