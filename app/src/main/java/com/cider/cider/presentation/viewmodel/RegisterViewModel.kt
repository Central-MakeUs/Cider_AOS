package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.domain.type.RegisterType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

): ViewModel() {
    //탭 상태
    private val _registerState = MutableLiveData<RegisterType>()
    val registerType: LiveData<RegisterType> get() = _registerState

    //전체(다음) 버튼
    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> get() = _buttonState

    //약관 자세히 보기 버튼 (1 -> 1번 자세히 보기, 2 -> 2번 자세히 보기, 0 -> 둘 다 접힌 상태)
    private val _detailState = MutableLiveData<Int>()
    val detailState: LiveData<Int> get() = _detailState

    init {
        _detailState.value = 0
    }
    fun serviceAgreementCheck() {

    }

    fun changeRegisterState(registerType: RegisterType) {
        _registerState.value = registerType
        checkButtonState()
    }

    fun checkButtonState() {
        _buttonState.value = true
    }

    fun setTermDetail(num: Int) {
        if (_detailState.value != num) {
            _detailState.value = num
        }
        else if (_detailState.value == num) {
            _detailState.value = 0
        }
        Log.d("TermTest","${_detailState.value}")
    }
}