package com.cider.cider.domain.type.challenge

enum class ParticipationStatus(val title: String?, val text: String) {
    RECRUITING("모집중","대기중"),
    ON_GOING("진행중","진행중"),
    COMPLETED(null,"챌린지 성공")
}