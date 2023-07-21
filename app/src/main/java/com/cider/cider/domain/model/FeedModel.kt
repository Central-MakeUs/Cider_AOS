package com.cider.cider.domain.model

import android.net.Uri
import com.cider.cider.domain.type.challenge.Challenge
import dagger.multibindings.IntoMap

data class FeedModel(
    val id: Int,
    val profile: Uri?,
    val nickname: String,
    val lv: Int,
    val date: String,
    val title: String,
    val content: String,
    val imageList: ImageCardModel?,
    val challengeModel: ChallengeModel,
    val like: Int,
    val likeCheck: Boolean = false
)


