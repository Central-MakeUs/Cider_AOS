package com.cider.cider.domain.repository

import android.net.Uri
import com.cider.cider.data.remote.model.RequestCertify
import com.cider.cider.data.remote.model.RequestChallengeCreate
import com.cider.cider.data.remote.model.ResponseCertifyDetail
import com.cider.cider.data.remote.model.ResponseMyChallenge
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeDetailModel
import com.cider.cider.domain.model.ChallengeListModel
import com.cider.cider.domain.model.MyPageModel
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import okhttp3.MultipartBody
import retrofit2.Response

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

    suspend fun deleteChallenge(id: Int): Boolean

    suspend fun getMyPage(): MyPageModel?

    suspend fun getMyChallenge(): Response<ResponseMyChallenge>?

    suspend fun getChallengeDetail(id: Int): ChallengeDetailModel?
    suspend fun getCertifyDetail(id: Int, filter: Filter): Response<ResponseCertifyDetail>?

    suspend fun getChallengeParticipate(): List<ChallengeListModel>?
    suspend fun getChallengeMyPageParticipate(): List<ChallengeListModel>?
    suspend fun getChallengeCertify(id: Int): List<CertifyModel>?
    suspend fun getChallengeLike(): List<ChallengeCardModel>?

    suspend fun patchProfile(name: String): Boolean
    suspend fun patchProfileImage(image: MultipartBody.Part): Boolean

    suspend fun participateChallenge(id: Int): Boolean
    suspend fun postChallengeCertify(
        param: RequestCertify,
        image1: List<MultipartBody.Part>
    ): Boolean
}