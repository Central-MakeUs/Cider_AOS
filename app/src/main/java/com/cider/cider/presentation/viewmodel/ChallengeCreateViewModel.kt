package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.domain.type.ChallengeButtonState
import com.cider.cider.domain.type.challenge.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengeCreateViewModel @Inject constructor(

):ViewModel() {
    private val _challengeSelect = MutableLiveData<Challenge>()
    val challengeSelect: LiveData<Challenge> get() = _challengeSelect

    val challengeTitle = MutableLiveData<String>("")
    val challengeIntroduction = MutableLiveData<String>("")
    val challengeAuthentication = MutableLiveData<String>("")

    private val _capacity = MutableLiveData<Int>(3)
    val capacity: LiveData<Int> get() = _capacity

    private val _recruitmentPeriod = MutableLiveData<Int>(3)
    val recruitmentPeriod: LiveData<Int> get() = _recruitmentPeriod

    private val _challengePeriod = MutableLiveData<Int>(3)
    val challengePeriod: LiveData<Int> get() = _challengePeriod


    fun changeChallenge(challenge: Challenge) {
        _challengeSelect.value = challenge
        Log.d("TEST change challenge","$challenge")
    }

    fun clearTitle() {
        challengeTitle.value = ""
    }

    fun clearMission() {
        challengeAuthentication.value = ""
    }
}