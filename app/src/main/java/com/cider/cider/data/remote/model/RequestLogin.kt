package com.cider.cider.data.remote.model

data class RequestLogin(
    val socialType: String = "KAKAO",
    val clientType: String = "ANDROID"
) {
}