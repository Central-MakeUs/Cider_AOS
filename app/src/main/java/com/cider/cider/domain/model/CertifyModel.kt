package com.cider.cider.domain.model

import android.net.Uri
import com.cider.cider.domain.type.challenge.Category
import retrofit2.http.Url

data class CertifyModel(
    val id: Int,
    val certifyContent: String,
    val certifyLike: Int,
    val certifyName: String,
    val createdDate: String,
    val isLike: Boolean,
    val isExpand: Boolean = false,
    val challengeBranch: Category,
    val challengeName: String,
    val participateNum: Int,
    val memberLevel: Int,
    val memberName: String,
    val profilePath: Uri,
    val certifyImage: Uri?
)
