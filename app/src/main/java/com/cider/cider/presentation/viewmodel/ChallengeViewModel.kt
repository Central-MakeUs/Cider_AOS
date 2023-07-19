package com.cider.cider.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.R
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.domain.type.challenge.ParticipationStatus
import com.cider.cider.utils.getResourceUri
import javax.inject.Inject

class ChallengeViewModel @Inject constructor(
): ViewModel() {
    private val _challenge = MutableLiveData<List<ChallengeCardModel>>()
    val challenge: LiveData<List<ChallengeCardModel>> get() = _challenge



    init {
        getChallenge()
    }



    fun getChallenge() {
        val list: MutableList<ChallengeCardModel> = mutableListOf()

        Log.d("TEST Challenge","${challenge.hashCode()}")
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
        list.add(
            ChallengeCardModel(
                id = 7, participate = ParticipationStatus.RECRUITING,
                like = true, reward = false, category = Challenge.INVESTING,
                duration = 1, rank = null, title = "소비습관 고치기", people = 23,
                official = true, d_day = null
            )
        )
        list.add(
            ChallengeCardModel(
                id = 8, participate = ParticipationStatus.RECRUITING,
                like = true, reward = true, category = Challenge.SAVING,
                duration = 1, rank = null, title = "소비습관 고치기", people = 3,
                official = false, d_day = null
            )
        )
        list.add(
            ChallengeCardModel(
                id = 9, participate = ParticipationStatus.RECRUITING,
                like = false, reward = false, category = Challenge.SAVING,
                duration = 8, rank = null, title = "소비습관 고치기", people = 7,
                official = false, d_day = null
            )
        )
        list.add(
            ChallengeCardModel(
                id = 10, participate = ParticipationStatus.RECRUITING,
                like = false, reward = false, category = Challenge.SAVING,
                duration = 8, rank = null, title = "소비습관 고치기", people = 7,
                official = false, d_day = null
            )
        )
        _challenge.value = list
    }
}