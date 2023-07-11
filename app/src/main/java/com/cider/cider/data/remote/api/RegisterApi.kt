package com.cider.cider.data.remote.api

import com.cider.cider.data.remote.model.RequestLoginModel
import com.cider.cider.data.remote.model.ResponseLoginModel
import com.cider.cider.data.remote.model.ResponseRandomNickNameModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterApi {
    @Headers("Content-Type: application/json")
    @POST("/api/oauth/login")
    suspend fun postLogin(
        @Body params: RequestLoginModel
    ): Response<ResponseLoginModel>


    @GET("/api/member/nickname")
    suspend fun getRandomNickname(): Response<ResponseRandomNickNameModel>
}