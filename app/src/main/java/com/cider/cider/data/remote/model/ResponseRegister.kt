package com.cider.cider.data.remote.model

data class ResponseLoginModel(
    val accessToken: String,
    val refreshToken: String,
    val isNewMember: Boolean,
    val memberId: Int,
    val memberName: String,
    val birthday: String,
    val gender: String
)

data class ResponseRandomNickNameModel(
    val randomName: String
)

data class ResponseNicknameExist(
    val message: String,
)