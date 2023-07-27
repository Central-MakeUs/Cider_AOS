package com.cider.cider.data.remote.datasource

import android.util.Log
import com.cider.cider.data.remote.api.ChallengeApi
import com.cider.cider.data.remote.model.ResponseChallengeItem
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Challenge
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

    override suspend fun getCertifyHomeFeed() {
        TODO("Not yet implemented")
    }

    override suspend fun getChallengeHomeCategory(challenge: Challenge): List<ChallengeCardModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun getChallengeOfficial(filter: Filter): List<ChallengeCardModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun getChallengePopular(filter: Filter): List<ChallengeCardModel>? {
        TODO("Not yet implemented")
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
                duration = responseItem.recruitLeft, // 또는 challengeStatus와의 연관에 따라 적절한 값을 설정해야 합니다.
                rank = null, // 이 부분은 여기서 정의한 로직에 따라서 값을 지정하셔야 합니다.
                title = responseItem.challengeName,
                people = responseItem.participateNum,
                official = responseItem.isOfficial,
                d_day = null // 이 부분은 여기서 정의한 로직에 따라서 값을 지정하셔야 합니다.
            )
        }
    }
}