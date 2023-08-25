package com.cider.cider.data.remote.model

data class RequestCertifyLike(
    val certifyId: Int
)

data class RequestChallengeLike(
    val challengeId: Int
)

data class RequestChallengeCreate(
    val certifyMission: String,
    val challengeBranch: String,
    val challengeCapacity: Int,
    val challengeInfo: String,
    val challengeName: String,
    val challengePeriod: Int,
    val isPublic: Boolean,
    val recruitPeriod: Int
)

data class RequestParticipate(
    val challengeId: Int
)