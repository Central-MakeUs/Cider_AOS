package com.cider.cider.domain.model

data class ChallengeInfoModel(
    val certifyNum: Int,
    val certifyTime: String,
    val challengeCapacity: Int,
    val challengePeriod: String,
    val isReward: Boolean,
    val recruitPeriod: String
)
