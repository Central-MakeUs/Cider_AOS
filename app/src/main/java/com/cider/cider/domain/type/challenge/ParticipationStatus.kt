package com.cider.cider.domain.type.challenge

enum class ParticipationStatus(val title: String?, val text: String) {
    RECRUITING("모집중","대기중"),
    ON_GOING("참여가능","진행중"),
    IMPOSSIBLE("참여불가","모집 종료"),
    COMPLETED("종료","챌린지 성공")
}

enum class ChallengeStatus( val text: String) {
    RECRUITING("모집중"),
    POSSIBLE("진행중"),
    IMPOSSIBLE("참여불가"),
}

fun getParticipationStatus(challengeStatus: String?): ParticipationStatus {
    return when (challengeStatus) {
        "RECRUITING" -> ParticipationStatus.RECRUITING
        "POSSIBLE" -> ParticipationStatus.ON_GOING
        "IMPOSSIBLE" -> ParticipationStatus.IMPOSSIBLE
        else -> ParticipationStatus.COMPLETED
    }
}

fun getChallengeStatus(challengeStatus: String?): ChallengeStatus {
    return when (challengeStatus) {
        "RECRUITING" -> ChallengeStatus.RECRUITING
        "POSSIBLE" -> ChallengeStatus.POSSIBLE
        "IMPOSSIBLE" -> ChallengeStatus.IMPOSSIBLE
        else -> ChallengeStatus.IMPOSSIBLE
    }
}