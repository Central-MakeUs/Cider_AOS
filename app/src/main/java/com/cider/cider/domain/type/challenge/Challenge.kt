package com.cider.cider.domain.type.challenge

import com.cider.cider.R

enum class Challenge(val text: String, val colorResId: Int, val imageResId: Int, val comment: String) {
    INVESTING(
        "재테크",
        R.color.btn_mint,
        R.drawable.ic_investing,
        "돈을 버는 습관을 길러주는 재테크 챌린지를 소개해요"
    ),
    SAVING(
        "소비절약",
        R.color.btn_pink,
        R.drawable.ic_saving,
        "돈을 버는 습관을 길러주는 소비절약 챌린지를 소개해요"
    ),
    MONEY_MANAGEMENT(
        "돈 관리",
        R.color.btn_blue,
        R.drawable.ic_money_management,
        "돈을 버는 습관을 길러주는 돈관리 챌린지를 소개해요"
    ),
    FINANCIAL_LEARNING(
        "금융학습",
        R.color.btn_purple,
        R.drawable.ic_financial_learning,
        "돈을 버는 습관을 길러주는 금융학습 챌린지를 소개해요"
    ),
}