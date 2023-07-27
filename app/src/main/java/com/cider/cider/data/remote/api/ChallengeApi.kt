package com.cider.cider.data.remote.api

import com.cider.cider.data.remote.model.ResponseChallengeItem
import com.cider.cider.data.remote.model.ResponseChallengeList
import com.cider.cider.domain.type.Filter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChallengeApi {

    @GET("/api/challenge/{filter}")
    suspend fun getChallengeList(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/certify/home")
    suspend fun getCertifyHomeFeed(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/challenge/home/{category}")
    suspend fun getChallengeHomeCategory(
        @Path("category") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/challenge/home")
    suspend fun getChallengeHome(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/challenge/official/{filter}")
    suspend fun getChallengeOfficial(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/challenge/popular/{filter}")
    suspend fun getChallengePopular(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>
}