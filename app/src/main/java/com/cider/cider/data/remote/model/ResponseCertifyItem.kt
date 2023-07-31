package com.cider.cider.data.remote.model

data class ResponseCertifyItem(
    val certifyContent: String,
    val certifyLike: Int,
    val certifyName: String,
    val createdDate: String,
    val isLike: Boolean,
    val simpleChallengeResponseDto: SimpleChallengeResponseDto,
    val simpleMemberResponseDto: SimpleMemberResponseDto
)