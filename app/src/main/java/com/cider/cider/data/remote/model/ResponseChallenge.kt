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
    val certifyId: Int,
    val certifyContent: String,
    val certifyImageUrl: String?,
    val certifyLike: Int,
    val certifyName: String,
    val createdDate: String,
    val isLike: Boolean,
    val simpleChallengeResponseDto: SimpleChallengeResponseDto,
    val simpleMemberResponseDto: SimpleMemberResponseDto
)

data class ResponseChallengeCertifyList(
    val simpleChallengeResponseDto: SimpleChallengeResponseDto,
    val simpleMemberResponseDto: SimpleMemberResponseDto,
    val certifyResponseDtoList: List<CertifyResponseDtoList>
)

data class CertifyResponseDtoList(
    val certifyId: Int,
    val certifyContent: String,
    val certifyImageUrl: String?,
    val certifyLike: Int,
    val certifyName: String,
    val createdDate: String,
    val isLike: Boolean,
)

data class SimpleChallengeResponseDto(
    val challengeBranch: String,
    val challengeName: String,
    val participateNum: Int
)

data class SimpleMemberResponseDto(
    val memberLevel: Int,
    val memberName: String,
    val memberLevelName: String,
    val profilePath: String,
    val participateChallengeNum: Int,
)

data class ResponseCertifyLike(
    val message: String,
)

data class ResponseChallengeLike(
    val message: String,
)

data class ResponseChallengeCreate(
    val challengeId: Int
)

data class ResponseChallengeImageCreate(
    val message: String
)

data class ResponseChallengeParticipate(
    val challengeId: Int,
    val challengeName: String,
    val participateStatus: String,
)

data class ResponseCertify(
    val certifyId: Int
)