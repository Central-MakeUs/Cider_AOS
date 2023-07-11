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





    fun changeChallenge(challenge: Challenge) {
        _challengeSelect.value = challenge
        Log.d("TEST change challenge","$challenge")
    }
}