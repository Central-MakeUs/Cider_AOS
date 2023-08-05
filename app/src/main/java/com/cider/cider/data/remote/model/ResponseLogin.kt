package com.cider.cider.data.remote.model

data class ResponseLoginModel(
    val accessToken: String,
    val accessTokenExpireTime: String,
    val refreshToken: String,
    val refreshTokenExpireTime: String,
    val isNewMember: Boolean,
    val memberId: Int,
    val memberName: String?,
    val birthday: String?,
    val gender: String?,
    val isUpdatedMember: Boolean
)

data class ResponseRandomNickNameModel(
    val randomName: String
)

data class ResponseNicknameExist(
    val message: String,
)

data class ResponseMe(
    val memberId: Int,
    val memberName: String,
)

data class ResponseMember(
    val memberId: Int,
    val memberName: String,
    val memberBirth: String,
    val memberGender: String,
    val interestChallenge: String
)