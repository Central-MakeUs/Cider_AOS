package com.cider.cider.domain.repository

import android.net.Uri
import com.cider.cider.data.remote.model.RequestChallengeCreate
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import okhttp3.MultipartBody

interface ChallengeRepository {
    suspend fun getChallengeList(filter: Filter): List<ChallengeCardModel>?
    suspend fun getCertifyHome(): List<CertifyModel>?

    suspend fun getChallengeCategory(challenge: Category): List<ChallengeCardModel>?
    suspend fun getChallengeOfficial(filter: Filter): List<ChallengeCardModel>?
    suspend fun getChallengePopular(filter: Filter): List<ChallengeCardModel>?

    suspend fun postCertifyLike(id: Int): Boolean
    suspend fun deleteCertifyLike(id: Int): Boolean

    suspend fun postChallengeLike(id: Int): Boolean
    suspend fun deleteChallengeLike(id: Int): Boolean

    suspend fun createChallenge(
        param: RequestChallengeCreate,
        image1: List<MultipartBody.Part>,
        image2: List<MultipartBody.Part>
    ): Boolean
}