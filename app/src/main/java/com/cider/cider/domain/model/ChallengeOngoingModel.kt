package com.cider.cider.domain.model

import com.cider.cider.domain.type.challenge.Category

data class ChallengeOngoingModel(
    val title: String,
    val challenge: Category,
    val total: Int,
    val current: Int,
    val during: Int,
)
