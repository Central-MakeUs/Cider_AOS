package com.cider.cider.domain.type.challenge

import com.cider.cider.R

enum class Challenge(val gradient: Int, val text: String) {
    INVESTING(
        R.drawable.selector_line_btn_mint,
        "재테크"
    ),
    SAVING(
        R.drawable.selector_line_btn_pink,
        "소비절약"
    ),
    MONEY_MANAGEMENT(
        R.drawable.selector_line_btn_yellow,
        "돈 관리"
    ),
    FINANCIAL_LEARNING(
        R.drawable.selector_line_btn_blue,
        "금융학습"
    ),
}