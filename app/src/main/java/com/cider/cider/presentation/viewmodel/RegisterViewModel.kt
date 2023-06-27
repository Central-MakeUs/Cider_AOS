package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.register.RegisterConsentFragment
import com.cider.cider.presentation.register.RegisterKeywordFragment
import com.cider.cider.presentation.register.RegisterNicknameFragment
import com.cider.cider.presentation.register.RegisterProfileFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

): ViewModel() {
    private val _registerState = MutableLiveData<RegisterType>()
    val registerType: LiveData<RegisterType> get() = _registerState

    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> get() = _buttonState

    fun serviceAgreementCheck() {

    }

    fun changeRegisterState(registerType: RegisterType) {
        _registerState.value = registerType
        checkButtonState()
    }

    fun checkButtonState() {
        _buttonState.value = true
    }
}