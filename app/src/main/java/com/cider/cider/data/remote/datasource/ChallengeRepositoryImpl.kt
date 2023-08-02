package com.cider.cider.data.remote.datasource

import android.net.Uri
import android.util.Log
import com.cider.cider.data.remote.api.ChallengeApi
import com.cider.cider.data.remote.model.RequestCertifyLike
import com.cider.cider.data.remote.model.ResponseCertifyItem
import com.cider.cider.data.remote.model.ResponseChallengeItem
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.domain.type.challenge.getChallengeCategory
import com.cider.cider.domain.type.challenge.getParticipationStatus
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val apiService: ChallengeApi
): ChallengeRepository {

    override suspend fun getChallengeList(filter: Filter): List<ChallengeCardModel>? {
        val data = apiService.getChallengeList(filter = Filter.LATEST.string)
        return when (data.code()) {
            200 -> mapResponseToChallengeCardModel(data.body())
            else -> null
        }
    }

    override suspend fun getCertifyHome(): List<CertifyModel>? {
        val data = apiService.getCertifyHome()
        return when (data.code()) {
            200 -> {
                Log.d("Test certify","${data.body()}")
                mapToCertifyModelList(data.body())
            }
            else -> null
        }
    }

    override suspend fun getChallengeCategory(challenge: Category): List<ChallengeCardModel>? {
        val data = apiService.getChallengeCategory(category = challenge.api)
        Log.d("TEST API","${data.body()}")
        return when (data.code()) {
            200 -> mapResponseToChallengeCardModel(data.body())
            else -> null
        }
    }

    override suspend fun getChallengeOfficial(filter: Filter): List<ChallengeCardModel>? {
        val data = apiService.getChallengeOfficial(filter = Filter.LATEST.string)
        return when (data.code()) {
            200 -> mapResponseToChallengeCardModel(data.body())
            else -> null
        }
    }

    override suspend fun getChallengePopular(filter: Filter): List<ChallengeCardModel>? {
        val data = apiService.getChallengePopular(filter = Filter.LATEST.string)
        return when (data.code()) {
            200 -> mapResponseToChallengeCardModel(data.body())
            else -> null
        }
    }

    override suspend fun postCertifyLike(id: Int): Boolean {
        val data = apiService.postCertifyLike(RequestCertifyLike(id))
        return when (data.code()) {
            200 -> true
            else -> false
        }
    }

    override suspend fun deleteCertifyLike(id: Int): Boolean {
        val data = apiService.deleteCertifyLike(id)
        return when (data.code()) {
            200 -> true
            else -> false
        }
    }


    private fun mapResponseToChallengeCardModel(responseList: List<ResponseChallengeItem>?)
    : List<ChallengeCardModel>? {
        return responseList?.map { responseItem ->
            ChallengeCardModel(
                id = responseItem.challengeId,
                participate = getParticipationStatus(responseItem.challengeStatus),
                like = responseItem.isLike,
                reward = responseItem.isReward,
                category = getChallengeCategory(responseItem.interestField),
                duration = responseItem.challengePeriod, // 또는 challengeStatus와의 연관에 따라 적절한 값을 설정해야 합니다.
                rank = null, // 이 부분은 여기서 정의한 로직에 따라서 값을 지정하셔야 합니다.
                title = responseItem.challengeName,
                people = responseItem.participateNum,
                official = responseItem.isOfficial,
                d_day = responseItem.recruitLeft // 이 부분은 여기서 정의한 로직에 따라서 값을 지정하셔야 합니다.
            )
        }
    }

    private fun mapToCertifyModelList(responseList: List<ResponseCertifyItem>?): List<CertifyModel>? {
        return responseList?.map { response ->
            CertifyModel(
                id = response.certifyId,
                certifyContent = response.certifyContent,
                certifyLike = response.certifyLike,
                certifyName = response.certifyName,
                createdDate = response.createdDate,
                isLike = response.isLike,
                challengeBranch = getChallengeCategory(response.simpleChallengeResponseDto.challengeBranch),
                challengeName = response.simpleChallengeResponseDto.challengeName,
                participateNum = response.simpleChallengeResponseDto.participateNum,
                memberLevel = response.simpleMemberResponseDto.memberLevel,
                memberName = response.simpleMemberResponseDto.memberName,
                profilePath = Uri.parse(response.simpleMemberResponseDto.profilePath),
                certifyImage = if (response.certifyImageUrl != null ) Uri.parse(response.certifyImageUrl) else null
            )
        }
    }
}