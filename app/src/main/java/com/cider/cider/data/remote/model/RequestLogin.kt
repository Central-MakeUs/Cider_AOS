package com.cider.cider.data.remote.model

data class RequestLoginModel(
    val socialType: String = "KAKAO",
    val clientType: String = "ANDROID"
) {
}

data class RequestMember(
    val memberName: String,
    val memberBirth: String,
    val memberGender: String,
    val interestChallenge: String
)