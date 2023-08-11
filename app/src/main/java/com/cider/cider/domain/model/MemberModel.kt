package com.cider.cider.domain.model

import android.net.Uri

data class MemberModel(
    val memberLevelName: String,
    val memberName: String,
    val participateNum: Int,
    val profilePath: Uri
)
