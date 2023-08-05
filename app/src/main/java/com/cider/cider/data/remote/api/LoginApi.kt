package com.cider.cider.data.remote.api

import com.cider.cider.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {
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

    @GET("/api/member/me")
    suspend fun getMe(
        @Header("Authorization") accessCode: String,
    ): Response<ResponseMe>

    @PATCH("/api/member")
    suspend fun patchMember(
        @Header("Authorization") accessToken: String,
        @Body param: RequestMember
    ): Response<ResponseMember>
}