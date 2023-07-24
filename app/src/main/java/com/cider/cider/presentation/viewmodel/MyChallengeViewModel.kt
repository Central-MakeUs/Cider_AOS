package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.domain.model.ChallengeCardFinishModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeOngoingModel
import com.cider.cider.domain.model.ChallengeReviewModel
import com.cider.cider.domain.type.ReviewType
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.domain.type.challenge.ParticipationStatus
import javax.inject.Inject

class MyChallengeViewModel @Inject constructor(

):ViewModel(){
    private val _challengeFinish = MutableLiveData<List<ChallengeCardFinishModel>>()
    val challengeFinish: LiveData<List<ChallengeCardFinishModel>> get() = _challengeFinish

    private val _challengeOngoing = MutableLiveData<List<ChallengeOngoingModel>>()
    val challengeOngoing: LiveData<List<ChallengeOngoingModel>> get() = _challengeOngoing

    private val _challengeReview = MutableLiveData<List<ChallengeReviewModel>>()
    val challengeReview: LiveData<List<ChallengeReviewModel>> get() = _challengeReview

    init {
        test1()
        test2()
        test3()
    }

    fun test1() {
        val list: MutableList<ChallengeCardFinishModel> = mutableListOf()

        list.add(
            ChallengeCardFinishModel(
                id = 1, participate = ParticipationStatus.RECRUITING,
                like = false, reward = true, category = Challenge.SAVING,
                duration = 1, rank = 1, title = "소비습관 고치기1", people = 5,
                official = true, d_day = 23, success = true
            )
        )
        list.add(
            ChallengeCardFinishModel(
                id = 2, participate = ParticipationStatus.RECRUITING,
                like = false, reward = true, category = Challenge.SAVING,
                duration = 1, rank = 1, title = "소비습관 고치기2", people = 15,
                official = true, d_day = 23, success = false
            )
        )
        _challengeFinish.value = list
    }

    fun test2() {
        val list: MutableList<ChallengeOngoingModel> = mutableListOf()

        list.add(
            ChallengeOngoingModel(
                id = 1, title = "소비습관 고치기", challenge =  Challenge.SAVING, total = 30,
                current = 24, during = 10
            )
        )
        list.add(
            ChallengeOngoingModel(
                id = 2, title = "소비습관 고치기2", challenge =  Challenge.SAVING, total = 30,
                current = 15, during = 7
            )
        )
        _challengeOngoing.value = list
    }

    fun test3() {
        val list: MutableList<ChallengeReviewModel> = mutableListOf()

        list.add(
            ChallengeReviewModel(
                id = 1, title = "소비습관 고치기", challenge = Challenge.SAVING , reviewType = ReviewType.REVIEW,
                date = null
            )
        )
        list.add(
            ChallengeReviewModel(
                id = 2, title = "소비습관 고치기2", challenge = Challenge.INVESTING , reviewType = ReviewType.APPROVED,
                date = null
            )
        )
        list.add(
            ChallengeReviewModel(
                id = 3, title = "소비습관 고치기3", challenge = Challenge.FINANCIAL_LEARNING , reviewType = ReviewType.FAILED,
                date = null
            )
        )
        list.add(
            ChallengeReviewModel(
                id = 4, title = "소비습관 고치기4", challenge = Challenge.MONEY_MANAGEMENT , reviewType = ReviewType.REJECTED,
                date = null
            )
        )

        _challengeReview.value = list
    }

}