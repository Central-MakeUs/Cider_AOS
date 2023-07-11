package com.cider.cider.data.remote.model

data class ResponseLogin(
    val accessToken: String,
    val refreshToken: String,
    val isNewMember: Boolean,
    val memberId: Int,
    val memberName: String,
    val birthday: String,
    val gender: String
)
