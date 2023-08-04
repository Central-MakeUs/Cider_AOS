package com.cider.cider.domain.model

import com.cider.cider.domain.type.ReviewType
import com.cider.cider.domain.type.challenge.Category

data class ChallengeReviewModel(
    val id: Int,
    val title: String,
    val challenge: Category,
    val reviewType: ReviewType,
    val date: String?
)
