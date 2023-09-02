package com.cider.cider.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.R
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.utils.getResourceUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeHomeViewModel @Inject constructor(
    private val repository: ChallengeRepository
):ViewModel() {
    private val _tabState = MutableLiveData<Category>()
    val tabState: LiveData<Category> get() = _tabState

    init {
        _tabState.value = Category.INVESTING
    }

    fun tabSelect(challenge: Category) {
        _tabState.value = challenge
    }

    suspend fun checkChallengeParticipateList(): Boolean {
        return repository.getChallengeParticipate().isNullOrEmpty() //비어있으면 true
    }
}