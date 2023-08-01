package com.cider.cider.domain.model

import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.domain.type.challenge.ParticipationStatus

data class ChallengeCardFinishModel(
    val id: Int,
    val success: Boolean,
    val participate: ParticipationStatus,
    val like: Boolean = false,
    val reward: Boolean = false,
    val category: Category,
    val duration: Int,
    val rank: Int?,
    val title: String,
    val people: Int,
    val official: Boolean = false,
    val d_day: Int?
)
