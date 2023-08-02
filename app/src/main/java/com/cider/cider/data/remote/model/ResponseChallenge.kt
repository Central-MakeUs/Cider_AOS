package com.cider.cider.data.remote.model

data class ResponseChallengeItem(
    val challengeId: Int,
    val challengeName: String,
    val challengeStatus: String,
    val interestField: String,
    val isLike: Boolean,
    val participateNum: Int,
    val recruitLeft: Int,
    val challengePeriod: Int,
    val isOfficial: Boolean,
    val isReward: Boolean,
)

data class ResponseCertifyItem(
    val certifyContent: String,
    val certifyLike: Int,
    val certifyName: String,
    val createdDate: String,
    val isLike: Boolean,
    val simpleChallengeResponseDto: SimpleChallengeResponseDto,
    val simpleMemberResponseDto: SimpleMemberResponseDto
)

data class SimpleChallengeResponseDto(
    val challengeBranch: String,
    val challengeName: String,
    val participateNum: Int
)

data class SimpleMemberResponseDto(
    val memberLevel: Int,
    val memberName: String,
    val profilePath: String
)