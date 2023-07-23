package com.cider.cider.domain.model

import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.domain.type.challenge.ParticipationStatus

data class ChallengeCardFinishModel(
    val id: Int,
    val success: Boolean,
    val like: Boolean = false,
    val reward: Boolean = false,
    val category: Challenge,
    val duration: Int,
    val rank: Int?,
    val title: String,
    val people: Int,
    val official: Boolean = false,
    val d_day: Int?
)
