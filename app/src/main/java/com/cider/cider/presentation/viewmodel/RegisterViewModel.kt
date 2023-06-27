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
    private val _registerState = MutableLiveData<RegisterType>()
    val registerType: LiveData<RegisterType> get() = _registerState

    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> get() = _buttonState

    init {
        _buttonState.value = true
        _registerState.value = RegisterType.SERVICE_AGREEMENT //초기 세팅
    }

    fun serviceAgreementCheck() {

    }
}