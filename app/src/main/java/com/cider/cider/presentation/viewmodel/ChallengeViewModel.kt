package com.cider.cider.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.domain.type.challenge.ParticipationStatus
import javax.inject.Inject

class ChallengeViewModel @Inject constructor(

): ViewModel() {
    private val _popularChallenge = MutableLiveData<List<ChallengeCardModel>>()
    val popularChallenge: LiveData<List<ChallengeCardModel>> get() = _popularChallenge

    private val _officialChallenge = MutableLiveData<List<ChallengeCardModel>>()
    val officialChallenge: LiveData<List<ChallengeCardModel>> get() = _officialChallenge

    init {
        testItem()
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
    }
}