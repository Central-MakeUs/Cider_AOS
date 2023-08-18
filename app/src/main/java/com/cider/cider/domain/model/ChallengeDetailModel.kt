package com.cider.cider.domain.model

import android.net.Uri
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.domain.type.challenge.ChallengeStatus

data class ChallengeDetailModel(
    val challengeId: Int,
    val category: Category,
    val challengeStatus: ChallengeStatus,
    val myStatus: String,
    val challengeName: String,
    val challengeCapacity: Int,
    val challengeIntro: String,
    val challengeLikeNum: Int,
    val isLike: Boolean,
    val participateNum: Int,
    val certifyMission: String,
    val failureExampleImage: Uri?,
    val successExampleImage: Uri?,
    val certifyRule: String,
    val failureRule: String,
    val challengeInfo: ChallengeInfoModel,
    val condition: ChallengeCondition,
    val member: MemberModel
) {
}