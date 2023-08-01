package com.cider.cider.domain.model

import com.cider.cider.domain.type.challenge.Category

data class ChallengeModel(
    val id: Int,
    val challengeType: Category,
    val title: String,
    val people: Int
)
