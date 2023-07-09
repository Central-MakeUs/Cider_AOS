package com.cider.cider.domain.type

data class ChallengeButtonState(
    val investing: Boolean = true,
    val money_management: Boolean = true,
    val financial_learning: Boolean = true,
    val saving: Boolean = true
) {
}