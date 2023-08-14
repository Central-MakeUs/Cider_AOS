package com.cider.cider.data.remote.model

data class ResponseChallengeDetail(
    val certifyMissionResponseDto: CertifyMissionResponseDto,
    val challengeBranch: String,
    val challengeCapacity: Int,
    val challengeConditionResponseDto: ChallengeConditionResponseDto,
    val challengeId: Int,
    val challengeInfoResponseDto: ChallengeInfoResponseDto,
    val challengeIntro: String,
    val challengeLikeNum: Int,
    val challengeName: String,
    val challengeRuleResponseDto: ChallengeRuleResponseDto,
    val challengeStatus: String,
    val isLike: Boolean,
    val myChallengeStatus: String,
    val participateNum: Int,
    val simpleMemberResponseDto: SimpleMemberResponseDto
)

data class CertifyMissionResponseDto(
    val certifyMission: String,
    val failureExampleImage: String,
    val successExampleImage: String
)

data class ChallengeConditionResponseDto(
    val averageCondition: Int,
    val challengePeriod: Int,
    val myCondition: Int,
    val ongoingDate: Int
)

data class ChallengeInfoResponseDto(
    val certifyNum: Int,
    val certifyTime: String,
    val challengeCapacity: Int,
    val challengePeriod: String,
    val isReward: Boolean,
    val recruitPeriod: String
)

data class ChallengeRuleResponseDto(
    val certifyRule: String,
    val failureRule: String
)

data class ResponseCertifyDetail(
    val certifyImageUrlList: List<String>,
    val challengeBranch: String,
    val challengeCapacity: Int,
    val challengeId: Int,
    val challengeName: String,
    val participateNum: Int,
    val simpleCertifyResponseDtoList: List<SimpleCertifyResponseDto>
)

data class SimpleCertifyResponseDto(
    val certifyContent: String,
    val certifyLike: Int,
    val certifyName: String,
    val createdDate: String,
    val isLike: Boolean,
    val simpleMemberResponseDto: SimpleMemberResponseDto
)
