package com.cider.cider.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeListViewModel @Inject constructor(
    private val repository: ChallengeRepository
): ViewModel() {
    private val _challenge = MutableLiveData<List<ChallengeCardModel>>()
    val challenge: LiveData<List<ChallengeCardModel>> get() = _challenge

    fun getChallenge() {
        viewModelScope.launch {
            _challenge.value = repository.getChallengeList(filter = Filter.LATEST)
        }
    }

    fun getChallengePopular() {
        viewModelScope.launch {
            _challenge.value = repository.getChallengePopular(filter = Filter.LATEST)
        }
    }

    fun getChallengeOfficial() {
        viewModelScope.launch {
            _challenge.value = repository.getChallengeOfficial(filter = Filter.LATEST)
        }
    }

    fun getChallengeCategory(challenge: Category) {
        viewModelScope.launch {
            _challenge.value = repository.getChallengeCategory(challenge)
        }
    }
}