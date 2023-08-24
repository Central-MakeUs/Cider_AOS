package com.cider.cider.domain.repository

import com.cider.cider.data.remote.model.RequestMember
import com.cider.cider.data.remote.model.ResponseLoginModel
import com.cider.cider.data.remote.model.ResponseMe
import retrofit2.Response

interface LoginRepository {
    suspend fun postLogin(header: String): Response<ResponseLoginModel>?
    suspend fun getRandomNickName(): String
    suspend fun getNickNameExist(nickname: String): Boolean
    suspend fun patchMember(accessToken: String, param: RequestMember): Boolean

    suspend fun getLoginMe(): Boolean
    suspend fun postLogout(): Boolean
    suspend fun postSignOut(): Boolean
}