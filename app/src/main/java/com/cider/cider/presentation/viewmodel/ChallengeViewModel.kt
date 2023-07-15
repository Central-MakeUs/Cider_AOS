package com.cider.cider.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.domain.type.challenge.ParticipationStatus
import javax.inject.Inject

class ChallengeViewModel @Inject constructor(

): ViewModel() {
    private val _popularChallenge = MutableLiveData<List<ChallengeCardModel>>()
    val popularChallenge: LiveData<List<ChallengeCardModel>> get() = _popularChallenge

    private val _officialChallenge = MutableLiveData<List<ChallengeCardModel>>()
    val officialChallenge: LiveData<List<ChallengeCardModel>> get() = _officialChallenge

    private val _categoryChallenge = MutableLiveData<List<ChallengeCardModel>>()
    val categoryChallenge: LiveData<List<ChallengeCardModel>> get() = _categoryChallenge

    private val _feed = MutableLiveData<List<FeedModel>>()
    val feed: LiveData<List<FeedModel>> get() = _feed

    init {
        testItem()
        testFeed()
    }

    private fun testItem() {
        val list: MutableList<ChallengeCardModel> = mutableListOf()
        list.add(
            ChallengeCardModel(
                id = 1, participate = ParticipationStatus.RECRUITING,
                like = false, reward = true, category = Challenge.SAVING,
                duration = 1, rank = 1, title = "소비습관 고치기", people = 5,
                official = true, d_day = 23
            )
        )
        list.add(
            ChallengeCardModel(
                id = 2, participate = ParticipationStatus.ON_GOING,
                like = true, reward = false, category = Challenge.FINANCIAL_LEARNING,
                duration = 1, rank = 2, title = "소비습관 고치기", people = 25,
                official = true, d_day = 23
            )
        )
        list.add(
            ChallengeCardModel(
                id = 3, participate = ParticipationStatus.COMPLETED,
                like = false, reward = false, category = Challenge.MONEY_MANAGEMENT,
                duration = 1, rank = 3, title = "소비습관 고치기", people = 15,
                official = true, d_day = null
            )
        )
        list.add(
            ChallengeCardModel(
                id = 4, participate = ParticipationStatus.RECRUITING,
                like = true, reward = false, category = Challenge.INVESTING,
                duration = 1, rank = null, title = "소비습관 고치기", people = 23,
                official = true, d_day = null
            )
        )
        list.add(
            ChallengeCardModel(
                id = 5, participate = ParticipationStatus.RECRUITING,
                like = true, reward = true, category = Challenge.SAVING,
                duration = 1, rank = null, title = "소비습관 고치기", people = 3,
                official = false, d_day = null
            )
        )
        list.add(
            ChallengeCardModel(
                id = 6, participate = ParticipationStatus.RECRUITING,
                like = false, reward = false, category = Challenge.SAVING,
                duration = 8, rank = null, title = "소비습관 고치기", people = 7,
                official = false, d_day = null
            )
        )

        _popularChallenge.value = list
        _officialChallenge.value = list
        _categoryChallenge.value = list

    }

    private fun testFeed() {
        val list: MutableList<FeedModel> = mutableListOf()

        list.add(
            FeedModel(
                id = 1, profile = null, nickname = "오늘챌린지화이팅", lv = 1,
                date = "23.05.15 14:45", title = "오늘 챌린지 인증하는데", content = "하루 만보 걷기 챌린지는 쉽고 재미있게 만보를 걸을 수있는 챌린지로, 제가 맨날 일하다가 한번 입원하고 나서 심각성을 느끼고 만들게 된 멋진 챌린지",
                imageList = null, challengeModel = ChallengeModel(id = 1, challengeType = Challenge.SAVING, title = "소비습관 하루에 열심히 해 화이팅 챌린지", people = 86), like = 346, likeCheck = false)
            )
        list.add(
            FeedModel(
                id = 2, profile = null, nickname = "오늘챌린지화이팅", lv = 1,
                date = "23.05.15 14:45", title = "오늘 챌린지 인증하는데", content = "하루 만보 걷기 챌린지는 쉽고 재미있게 만보를 걸을 수있는 챌린지로, 제가 맨날 일하다가 한번 입원하고 나서 심각성을 느끼고 만들게 된 멋진 챌린지",
                imageList = null, challengeModel = ChallengeModel(id = 1, challengeType = Challenge.SAVING, title = "소비습관 하루에 열심히 해 화이팅 챌린지", people = 86), like = 346, likeCheck = false)
        )
        list.add(
            FeedModel(
                id = 3, profile = null, nickname = "오늘챌린지화이팅", lv = 1,
                date = "23.05.15 14:45", title = "오늘 챌린지 인증하는데", content = "하루 만보 걷기 챌린지는 쉽고 재미있게 만보를 걸을 수있는 챌린지로, 제가 맨날 일하다가 한번 입원하고 나서 심각성을 느끼고 만들게 된 멋진 챌린지",
                imageList = null, challengeModel = ChallengeModel(id = 1, challengeType = Challenge.SAVING, title = "소비습관 하루에 열심히 해 화이팅 챌린지", people = 86), like = 346, likeCheck = false)
        )
        _feed.value = list
    }

}