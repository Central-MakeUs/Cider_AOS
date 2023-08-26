package com.cider.cider.data.remote.model

data class RequestCertify(
    val challengeId: Int,
    val certifyName: String,
    val certifyContent: String
)
