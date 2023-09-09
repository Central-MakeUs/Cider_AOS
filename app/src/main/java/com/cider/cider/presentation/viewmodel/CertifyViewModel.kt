package com.cider.cider.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeListModel
import com.cider.cider.domain.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CertifyViewModel @Inject constructor(
    private val repository: ChallengeRepository
): ViewModel() {

    private val _certifyList = MutableLiveData<List<CertifyModel>>()
    val certifyList: LiveData<List<CertifyModel>> get() = _certifyList

    private val _challengeList = MutableLiveData<List<ChallengeListModel>>()
    val challengeList : LiveData<List<ChallengeListModel>> get() = _challengeList

    fun getCertify() {
        viewModelScope.launch {
            _certifyList.value = repository.getCertifyHome()
        }
    }

    fun changeLike(targetId: Int) {
        val beforeList = _certifyList.value?: mutableListOf()
        viewModelScope.launch {
            _certifyList.value =  beforeList.map {
                if (it.id == targetId) {
                    if (it.isLike) { //true 였다면 false 로 변경
                        if (repository.deleteCertifyLike(targetId))
                            it.copy(isLike = false, certifyLike = it.certifyLike - 1)
                        else
                            it
                    } else { //false 였다면 true 로 변경
                        if (repository.postCertifyLike(targetId))
                            it.copy(isLike = true, certifyLike = it.certifyLike + 1)
                        else
                            it
                    }
                } else {
                    it
                }
            }
        }
    }

    fun changeExpand(targetId: Int, isExpand: Boolean) {
        val beforeList = _certifyList.value?: mutableListOf()
        _certifyList.value =  beforeList.map {
            if (it.id == targetId) {
                it.copy(isExpand = !isExpand)
            } else {
                it
            }
        }
    }

    fun getCertify(id: Int) {
        viewModelScope.launch {
            _certifyList.value = repository.getChallengeCertify(id)
        }
    }

    suspend fun getChallengeList(): List<ChallengeListModel>? {
        return repository.getChallengeMyPageParticipate()
    }
}