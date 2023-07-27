package com.cider.cider.domain.type.challenge

enum class ParticipationStatus(val title: String?, val text: String) {
    RECRUITING("모집중","대기중"),
    ON_GOING("진행중","진행중"),
    IMPOSSIBLE("모집 종료","모집 종료"),
    COMPLETED(null,"챌린지 성공")
}

fun getParticipationStatus(challengeStatus: String): ParticipationStatus {
    return when (challengeStatus) {
        "RECRUITING" -> ParticipationStatus.RECRUITING
        "POSSIBLE" -> ParticipationStatus.ON_GOING
        "IMPOSSIBLE" -> ParticipationStatus.IMPOSSIBLE
        else -> ParticipationStatus.COMPLETED
    }
}