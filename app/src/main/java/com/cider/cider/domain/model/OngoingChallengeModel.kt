package com.cider.cider.domain.model

import com.cider.cider.domain.type.challenge.Challenge

data class OngoingChallengeModel(
    val id: Int,
    val title: String,
    val challenge: Challenge,
    val total: Int,
    val current: Int,
    val during: Int,
)
