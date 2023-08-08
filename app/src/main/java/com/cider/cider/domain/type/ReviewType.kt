package com.cider.cider.domain.type

import com.cider.cider.domain.type.challenge.Category

enum class ReviewType(val s: String){
    REVIEW("심사중"),
    APPROVED("심사완료"),
    REJECTED("반려"),
    FAILED("실패")
}

fun getReviewType(judgeField: String): ReviewType {
    return when (judgeField) {
        "JUDGING"-> ReviewType.REVIEW
        "COMPLETE"-> ReviewType.APPROVED
        "FAILURE"-> ReviewType.FAILED
        else -> ReviewType.FAILED
    }
}