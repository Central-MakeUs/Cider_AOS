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
import com.cider.cider.domain.type.getReviewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
):ViewModel(){
    private val _challengeFinish = MutableLiveData<List<ChallengeCardFinishModel>>()
    val challengeFinish: LiveData<List<ChallengeCardFinishModel>> get() = _challengeFinish

    private val _challengeOngoing = MutableLiveData<List<ChallengeOngoingModel>>()
    val challengeOngoing: LiveData<List<ChallengeOngoingModel>> get() = _challengeOngoing

    private val _challengeReview = MutableLiveData<List<ChallengeReviewModel>>()
    val challengeReview: LiveData<List<ChallengeReviewModel>> get() = _challengeReview

    private val _judgeNum = MutableLiveData<Int>(0)
    val judgeNum: LiveData<Int> get() = _judgeNum

    init {
        setMyChallenge()
    }

    private fun setMyChallenge() {
        viewModelScope.launch(Dispatchers.Main) {
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

                _challengeReview.value = data.body()?.judgingChallengeListResponseDto?.judgingChallengeResponseDtoList?.map {
                    ChallengeReviewModel(
                        id = it.challengeId,
                        title = it.challengeName,
                        challenge = getChallengeCategory( it.challengeBranch ),
                        reviewType = getReviewType(it.judgingStatus),
                        date = null
                    )
                }
                _judgeNum.value = data.body()?.judgingChallengeListResponseDto?.completeNum

                _challengeFinish.value = data.body()?.passedChallengeListResponseDto?.passedChallengeResponseDtoList?.map {
                    ChallengeCardFinishModel(
                        id = it.challengeId,
                        reward = false,
                        category = getChallengeCategory( it.challengeBranch ),
                        duration = 1,
                        title = it.challengeName,
                        people = it.successNum,
                        official = it.isOfficial,
                        success = it.isSuccess == "성공"
                    )
                }
            }
        }
    }

    fun deleteReviewChallenge(id: Int) {
        viewModelScope.launch {
            if (repository.deleteChallenge(id)) {
                val updatedItems = _challengeReview.value?.toMutableList() ?: mutableListOf()
                val itemToRemove = updatedItems.find { it.id == id }
                if (itemToRemove != null) {
                    updatedItems.remove(itemToRemove)
                    _challengeReview.value = updatedItems
                }
            }
        }

    }


}