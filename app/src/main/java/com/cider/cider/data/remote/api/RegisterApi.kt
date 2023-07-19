package com.cider.cider.data.remote.api

import com.cider.cider.data.remote.model.RequestLoginModel
import com.cider.cider.data.remote.model.ResponseLoginModel
import com.cider.cider.data.remote.model.ResponseNicknameExist
import com.cider.cider.data.remote.model.ResponseRandomNickNameModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterApi {
    @Headers("Content-Type: application/json")
    @POST("/api/oauth/login")
    suspend fun postLogin(
        @Header("Authorization") accessCode: String,
        @Body params: RequestLoginModel
    ): Response<ResponseLoginModel>


    @GET("/api/member/nicknames")
    suspend fun getRandomNickname(): Response<ResponseRandomNickNameModel>

    @GET("/api/member/nicknames/exists/{nickname}")
    suspend fun getNicknameExist(
        @Path("nickname") nickname: String
    ): Response<ResponseNicknameExist>
}