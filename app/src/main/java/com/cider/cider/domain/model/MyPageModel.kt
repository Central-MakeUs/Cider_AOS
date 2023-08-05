package com.cider.cider.domain.model

import android.net.Uri

data class MyPageModel(
    val name: String,
    val profileUri: Uri,
    val participateNum: Int,
    val level: Int,
    val certifyNum: Int,
    val likeChallengeNum: Int,
    val experienceLeft: Int,
    val levelPercent: Int,
    val percentComment: String,
    val myLevel: Int,
    val myLevelName: String,
    val nextLevelName: String,
    val nextLevel: Int,
)


