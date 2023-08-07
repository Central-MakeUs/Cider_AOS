package com.cider.cider.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.ChallengeCardFinishModel
import com.cider.cider.domain.model.ChallengeOngoingModel
import com.cider.cider.domain.model.ChallengeReviewModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.ReviewType
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.domain.type.challenge.ParticipationStatus
import com.cider.cider.domain.type.challenge.getChallengeCategory
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
):ViewModel(){
    private val _challengeFinish = MutableLiveData<List<ChallengeCardFinishModel>>()
    val challengeFinish: LiveData<List<ChallengeCardFinishModel>> get() = _challengeFinish

    private val _challengeOngoing = MutableLiveData<List<ChallengeOngoingModel>>()
    val challengeOngoing: LiveData<List<ChallengeOngoingModel>> get() = _challengeOngoing

    private val _challengeReview = MutableLiveData<List<ChallengeReviewModel>>()
    val challengeReview: LiveData<List<ChallengeReviewModel>> get() = _challengeReview

    init {
        test1()
        test3()
    }

    fun test1() {
        val list: MutableList<ChallengeCardFinishModel> = mutableListOf()

        list.add(
            ChallengeCardFinishModel(
                id = 1, participate = ParticipationStatus.RECRUITING,
                like = false, reward = true, category = Category.SAVING,
                duration = 1, rank = 1, title = "소비습관 고치기1", people = 5,
                official = true, d_day = 23, success = true
            )
        )
        list.add(
            ChallengeCardFinishModel(
                id = 2, participate = ParticipationStatus.RECRUITING,
                like = false, reward = true, category = Category.SAVING,
                duration = 1, rank = 1, title = "소비습관 고치기2", people = 15,
                official = true, d_day = 23, success = false
            )
        )
        _challengeFinish.value = list
    }


    fun test3() {
        val list: MutableList<ChallengeReviewModel> = mutableListOf()

        list.add(
            ChallengeReviewModel(
                id = 1, title = "소비습관 고치기", challenge = Category.SAVING , reviewType = ReviewType.REVIEW,
                date = null
            )
        )
        list.add(
            ChallengeReviewModel(
                id = 2, title = "소비습관 고치기2", challenge = Category.INVESTING , reviewType = ReviewType.APPROVED,
                date = null
            )
        )
        list.add(
            ChallengeReviewModel(
                id = 3, title = "소비습관 고치기3", challenge = Category.FINANCIAL_LEARNING , reviewType = ReviewType.FAILED,
                date = null
            )
        )
        list.add(
            ChallengeReviewModel(
                id = 4, title = "소비습관 고치기4", challenge = Category.MONEY_MANAGEMENT , reviewType = ReviewType.REJECTED,
                date = null
            )
        )

        _challengeReview.value = list
    }


    fun setMyChallenge() {

        viewModelScope.launch {
            val data = repository.getMyChallenge()

            if (data?.isSuccessful == true) {
                _challengeOngoing.value = data.body()?.ongoingChallengeListResponseDto?.ongoingChallengeResponseDtoList?.map {
                    ChallengeOngoingModel(
                        title = it.challengeName,
                        challenge = getChallengeCategory( it.challengeBranch ),
                        total = it.challengePeriod,
                        current = it.certifyNum,
                        during = it.ongoingDate
                    )
                }

/*                _challengeReview.value = data.body()?.judgingChallengeListResponseDto?.judgingChallengeResponseDtoList?.map {
                    ChallengeReviewModel(
                        id = it.challengeId,
                        title = it.challengeName,
                        challenge = getChallengeCategory( it.challengeBranch ),
                        reviewType = it.judgingStatus,
                        date = null
                    )
                }*/

            }
        }


    }

}