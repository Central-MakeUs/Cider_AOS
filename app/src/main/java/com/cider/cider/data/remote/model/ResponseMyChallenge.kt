package com.cider.cider.data.remote.model

data class ResponseMyChallenge(
    val judgingChallengeListResponseDto: JudgingChallengeListResponseDto,
    val ongoingChallengeListResponseDto: OngoingChallengeListResponseDto,
    val passedChallengeListResponseDto: PassedChallengeListResponseDto
)

data class JudgingChallengeListResponseDto(
    val completeNum: Int,
    val judgingChallengeNum: Int,
    val judgingChallengeResponseDtoList: List<JudgingChallengeResponseDto>
)

data class JudgingChallengeResponseDto(
    val challengeBranch: String,
    val challengeId: Int,
    val challengeName: String,
    val judgingStatus: String
)

data class OngoingChallengeListResponseDto(
    val ongoingChallengeNum: Int,
    val ongoingChallengeResponseDtoList: List<OngoingChallengeResponseDto>
)

data class OngoingChallengeResponseDto(
    val certifyNum: Int,
    val challengeBranch: String,
    val challengeName: String,
    val challengePeriod: Int,
    val ongoingDate: Int
)

data class PassedChallengeListResponseDto(
    val passedChallengeNum: Int,
    val passedChallengeResponseDtoList: List<PassedChallengeResponseDto>
)

data class PassedChallengeResponseDto(
    val challengeBranch: String,
    val challengeId: Int,
    val challengeName: String,
    val challengePeriod: Int,
    val isOfficial: Boolean,
    val isSuccess: String,
    val successNum: Int
)