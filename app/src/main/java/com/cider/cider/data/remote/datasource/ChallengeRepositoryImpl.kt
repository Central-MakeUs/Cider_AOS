package com.cider.cider.data.remote.datasource

import android.net.Uri
import android.util.Log
import com.cider.cider.data.remote.api.ChallengeApi
import com.cider.cider.data.remote.model.*
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeDetailModel
import com.cider.cider.domain.model.ChallengeInfoModel
import com.cider.cider.domain.model.MemberModel
import com.cider.cider.domain.model.MyPageModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.domain.type.challenge.getChallengeCategory
import com.cider.cider.domain.type.challenge.getParticipationStatus
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val apiService: ChallengeApi
): ChallengeRepository {

    override suspend fun getChallengeList(filter: Filter): List<ChallengeCardModel>? {
        val data = apiService.getChallengeList(filter = filter.string)
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
        val data = apiService.getChallengeOfficial(filter = filter.string)
        return when (data.code()) {
            200 -> mapResponseToChallengeCardModel(data.body())
            else -> null
        }
    }

    override suspend fun getChallengePopular(filter: Filter): List<ChallengeCardModel>? {
        val data = apiService.getChallengePopular(filter = filter.string)
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

    override suspend fun createChallenge(param: RequestChallengeCreate, image1: List<MultipartBody.Part>, image2: List<MultipartBody.Part>): Boolean {
        val data = apiService.postChallengeCreate(param)
        Log.d("TEST CREATE API DATA1","$data")
        if (data.body()?.challengeId != null) {
            val data2 = apiService.postChallengeCreateImage(data.body()?.challengeId!!, image1, image2 )
            return when (data2.code()) {
                200 -> true
                413 -> false
                else -> false
            }
        }
        return false
    }

    override suspend fun postChallengeLike(id: Int): Boolean {
        val data = apiService.postChallengeLike(RequestChallengeLike(id))
        Log.d("TEST api","$data")
        return when (data.code()) {
            200 -> true
            else -> false
        }
    }

    override suspend fun deleteChallengeLike(id: Int): Boolean {
        val data = apiService.deleteChallengeLike(id)
        return when (data.code()) {
            200 -> true
            else -> false
        }
    }

    override suspend fun getMyPage(): MyPageModel? {
        val data = apiService.getMyPage()
        return when (data.code()) {
            200 -> data.body()?.let { mapToMyPageModel(it) }
            else -> null
        }
    }

    override suspend fun getMyChallenge(): Response<ResponseMyChallenge>? {
        val data = apiService.getMyChallenge()
        return when (data.code()) {
            200 -> data
            else -> null
        }
    }

    override suspend fun getChallengeDetail(id: Int): ChallengeDetailModel? {
        val data = apiService.getChallengeDetail(id)
        return if (data.isSuccessful) {
            data.body()?.let { mapToChallengeDetail(it) }
        } else {
            null
        }
    }

    private fun mapToChallengeDetail(response: ResponseChallengeDetail): ChallengeDetailModel {
        return ChallengeDetailModel(
            challengeId = response.challengeId,
            category = getChallengeCategory(response.challengeBranch),
            challengeStatus = true,
            challengeName = response.challengeName?:"",
            challengeCapacity = response.challengeCapacity,
            challengeIntro = response.challengeIntro,
            challengeLikeNum = response.challengeLikeNum,
            isLike = response.isLike,
            participateNum = response.participateNum,
            certifyMission =  response.certifyMissionResponseDto.certifyMission,
            failureExampleImage = response.certifyMissionResponseDto.failureExampleImage?.let { Uri.parse(it) },
            successExampleImage = response.certifyMissionResponseDto.successExampleImage?.let { Uri.parse(it) },
            certifyRule = response.challengeRuleResponseDto.certifyRule,
            failureRule = response.challengeRuleResponseDto.failureRule,
            challengeInfo = ChallengeInfoModel(
                certifyNum = response.challengeInfoResponseDto.certifyNum,
                certifyTime = response.challengeInfoResponseDto.certifyTime,
                challengeCapacity = response.challengeInfoResponseDto.challengeCapacity,
                challengePeriod = response.challengeInfoResponseDto.challengePeriod,
                isReward = response.challengeInfoResponseDto.isReward,
                recruitPeriod = response.challengeInfoResponseDto.recruitPeriod
            ),
            member = MemberModel(
                memberLevelName = response.simpleMemberResponseDto.memberLevelName,
                memberName = response.simpleMemberResponseDto.memberName,
                participateNum = response.simpleMemberResponseDto.participateNum,
                profilePath = Uri.parse(response.simpleMemberResponseDto.profilePath)
            )
        )
    }

    private fun mapResponseToChallengeCardModel(responseList: List<ResponseChallengeItem>?) : List<ChallengeCardModel>? {
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

    private fun mapToMyPageModel(response: ResponseMyPage) : MyPageModel {
        return MyPageModel(
            name = response.simpleMember.memberName,
            profileUri = Uri.parse(response.simpleMember.profilePath),
            participateNum = response.simpleMember.participateNum,
            level = response.memberLevelInfo.myLevel,
            certifyNum = response.memberActivityInfo.myCertifyNum,
            likeChallengeNum = response.memberActivityInfo.myLikeChallengeNum,
            experienceLeft = response.memberLevelInfo.experienceLeft,
            levelPercent = response.memberLevelInfo.levelPercent,
            percentComment = response.memberLevelInfo.percentComment,
            myLevel = response.memberLevelInfo.myLevel,
            myLevelName = response.memberLevelInfo.myLevelName,
            nextLevel = response.memberLevelInfo.nextLevel.level,
            nextLevelName = response.memberLevelInfo.nextLevel.levelName
        )
    }
}