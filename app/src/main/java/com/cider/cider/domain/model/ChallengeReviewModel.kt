package com.cider.cider.domain.model

import com.cider.cider.domain.type.ReviewType
import com.cider.cider.domain.type.challenge.Challenge

data class ChallengeReviewModel(
    val id: Int,
    val title: String,
    val challenge: Challenge,
    val reviewType: ReviewType,
    val date: String?
)
