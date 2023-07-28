package com.cider.cider.domain.type.challenge

import com.cider.cider.R

enum class Challenge(val text: String, val colorResId: Int, val imageResId: Int, val comment: String, val api: String) {
    INVESTING(
        "재테크",
        R.color.btn_mint,
        R.drawable.ic_investing,
        "돈버는 습관을 길러주는 챌린지를 소개해요",
        "T"
    ),
    SAVING(
        "소비절약",
        R.color.btn_pink,
        R.drawable.ic_saving,
        "나의 재정 상태를 파악하고, 계획적인 습관을 길러주는 챌린지를 소개해요",
        "C"
    ),
    MONEY_MANAGEMENT(
        "돈 관리",
        R.color.btn_blue,
        R.drawable.ic_money_management,
        "재테크, 금융, 투자에 대한 지식을 쌓을 수 있는 챌린지를 소개해요",
        "M"
    ),
    FINANCIAL_LEARNING(
        "금융학습",
        R.color.btn_purple,
        R.drawable.ic_financial_learning,
        "불필요한 지출을 줄이고, 절약하는 습관을 길러주는 챌린지를 소개해요",
        "L"
    ),
}

fun getChallengeCategory(interestField: String): Challenge {
    return when (interestField) {
        "TECHNOLOGY"-> Challenge.INVESTING
        "MONEY"-> Challenge.MONEY_MANAGEMENT
        "LEARNING"-> Challenge.FINANCIAL_LEARNING
        "SAVING"-> Challenge.SAVING
        else -> Challenge.INVESTING
    }
}