package com.cider.cider.domain.model

import com.cider.cider.domain.type.challenge.Challenge

data class ChallengeModel(
    val id: Int,
    val challengeType: Challenge,
    val title: String,
    val people: Int
)
