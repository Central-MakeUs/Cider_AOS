package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.domain.model.ImageCardModel
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

    private val _recruitmentPeriod = MutableLiveData<Int>(1)
    val recruitmentPeriod: LiveData<Int> get() = _recruitmentPeriod

    private val _challengePeriod = MutableLiveData<Int>(1)
    val challengePeriod: LiveData<Int> get() = _challengePeriod

    private val _successImageList: MutableLiveData<List<ImageCardModel>> = MutableLiveData()
    val successImageList: LiveData<List<ImageCardModel>> get() = _successImageList

    private val _failImageList: MutableLiveData<List<ImageCardModel>> = MutableLiveData()
    val failImageList: LiveData<List<ImageCardModel>> get() = _failImageList

    private val _checkList = MutableLiveData<BooleanArray>(BooleanArray(4))
    val checkList: LiveData<BooleanArray> get() = _checkList

    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> get() = _buttonState

    private val _buttonState2 = MutableLiveData<Boolean>()
    val buttonState2: LiveData<Boolean> get() = _buttonState2
    init {
        _successImageList.value = mutableListOf()
    }

    fun onClear() {
        challengeTitle.value = ""
        challengeIntroduction.value = ""
        challengeAuthentication.value = ""
        _capacity.value = 3
        _recruitmentPeriod.value = 1
        _challengePeriod.value = 1
        _successImageList.value = arrayListOf()
        _failImageList.value = arrayListOf()
        Log.d("TEST asdf","$123")
    }

    fun changeChallenge(challenge: Challenge) {
        _challengeSelect.value = challenge
    }

    fun clearTitle() {
        challengeTitle.value = ""
    }

    fun clearMission() {
        challengeAuthentication.value = ""
    }

    fun checkChallengeInput(): Boolean {
        return !challengeAuthentication.value.isNullOrEmpty() ||
        !challengeIntroduction.value.isNullOrEmpty() ||
        !challengeTitle.value.isNullOrEmpty()
    } //값이 하나 라도 있으면 true

    fun addImageSuccess(imageCardModel: ImageCardModel) {
        val currentList = _successImageList.value?.toMutableList() ?: mutableListOf()
        if (currentList.size >= 1) {
            currentList.removeAt(0)
        }
        currentList.add(imageCardModel)
        _successImageList.value = (currentList)
    }

    fun addImageFail(imageCardModel: ImageCardModel) {
        val currentList = _failImageList.value?.toMutableList() ?: mutableListOf()
        if (currentList.size >= 1) {
            currentList.removeAt(0)
        }
        currentList.add(imageCardModel)
        _failImageList.value = (currentList)
        _failImageList.postValue(currentList)
    }

    fun changeCapacity(value: Int) {
        _capacity.value = value
    }

    fun changeRecruitmentPeriod(value: Int) {
        _recruitmentPeriod.value = value
    }

    fun changeChallengePeriod(value: Int) {
        _challengePeriod.value = value
    }

    fun changeCheckState(num: Int) {
        val array = _checkList.value?: BooleanArray(4){false}
        array[num] = !array[num]
        _checkList.value = array

        checkButtonState2()
    }

    private fun checkButtonState() {
        _buttonState.value = (!challengeTitle.value.isNullOrEmpty() &&
        !challengeIntroduction.value.isNullOrEmpty() && !challengeAuthentication.value.isNullOrEmpty())
    }

    private fun checkButtonState2() {
        _buttonState2.value = _checkList.value?.all { it }
    }
}