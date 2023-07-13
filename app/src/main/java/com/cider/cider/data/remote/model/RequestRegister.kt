package com.cider.cider.data.remote.model

data class RequestLoginModel(
    val socialType: String = "KAKAO",
    val clientType: String = "ANDROID"
) {
}