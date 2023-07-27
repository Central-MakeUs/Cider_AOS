package com.cider.cider.domain.repository

import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Challenge

interface ChallengeRepository {
    suspend fun getChallengeList(filter: Filter): List<ChallengeCardModel>?
    suspend fun getCertifyHomeFeed()

    suspend fun getChallengeHomeCategory(challenge: Challenge): List<ChallengeCardModel>?
    suspend fun getChallengeOfficial(filter: Filter): List<ChallengeCardModel>?
    suspend fun getChallengePopular(filter: Filter): List<ChallengeCardModel>?

}