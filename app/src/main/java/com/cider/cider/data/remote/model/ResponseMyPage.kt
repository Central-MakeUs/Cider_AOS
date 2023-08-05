package com.cider.cider.data.remote.model

data class ResponseMyPage(
    val memberActivityInfo: MemberActivityInfo,
    val memberLevelInfo: MemberLevelInfo,
    val simpleMember: SimpleMember
)

data class SimpleMember(
    val memberLevelName: String,
    val memberName: String,
    val participateNum: Int,
    val profilePath: String
)

data class CurrentLevel(
    val level: Int,
    val levelName: String,
    val requiredExperience: Int
)

data class MemberActivityInfo(
    val myCertifyNum: Int,
    val myLevel: Int,
    val myLikeChallengeNum: Int
)

data class MemberLevelInfo(
    val currentLevel: CurrentLevel,
    val experienceLeft: Int,
    val levelPercent: Int,
    val myLevel: Int,
    val myLevelName: String,
    val nextLevel: NextLevel,
    val percentComment: String
)

data class NextLevel(
    val level: Int,
    val levelName: String,
    val requiredExperience: Int
)