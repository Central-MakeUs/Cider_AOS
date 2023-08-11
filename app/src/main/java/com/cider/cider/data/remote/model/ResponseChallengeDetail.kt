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