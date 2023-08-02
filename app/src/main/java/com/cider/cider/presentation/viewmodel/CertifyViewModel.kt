package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CertifyViewModel @Inject constructor(
    private val repository: ChallengeRepository
): ViewModel() {

    private val _certify = MutableLiveData<List<CertifyModel>>()
    val certify: LiveData<List<CertifyModel>> get() = _certify

    init {
        getCertify()
    }

    private fun getCertify() {
        viewModelScope.launch {
            _certify.value = repository.getCertifyHome()
        }
    }

    fun changeLike(targetId: Int) {
        val beforeList = _certify.value?: mutableListOf()
        viewModelScope.launch {
            _certify.value =  beforeList.map {
                if (it.id == targetId) {
                    if (it.isLike) { //true 였다면 false 로 변경
                        it.copy(isLike = false, certifyLike = it.certifyLike - 1)
                    } else { //false 였다면 true 로 변경
                        it.copy(isLike = true, certifyLike = it.certifyLike + 1)
                    }
                } else {
                    it
                }
            }
        }
    }

    fun changeExpand(targetId: Int, isExpand: Boolean) {
        val beforeList = _certify.value?: mutableListOf()
        _certify.value =  beforeList.map {
            if (it.id == targetId) {
                it.copy(isExpand = isExpand)
            } else {
                it
            }
        }
        Log.d("TEST","??")
    }
}