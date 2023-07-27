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

data class ResponseChallengeList(
    val challengeList: ArrayList<ResponseChallengeItem>
)
