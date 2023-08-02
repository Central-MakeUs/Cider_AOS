package com.cider.cider.domain.repository

import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category

interface ChallengeRepository {
    suspend fun getChallengeList(filter: Filter): List<ChallengeCardModel>?
    suspend fun getCertifyHome(): List<CertifyModel>?

    suspend fun getChallengeCategory(challenge: Category): List<ChallengeCardModel>?
    suspend fun getChallengeOfficial(filter: Filter): List<ChallengeCardModel>?
    suspend fun getChallengePopular(filter: Filter): List<ChallengeCardModel>?

}