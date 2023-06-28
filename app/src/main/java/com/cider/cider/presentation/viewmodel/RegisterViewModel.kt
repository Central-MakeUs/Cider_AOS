package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.type.EditTextState
import com.cider.cider.domain.type.RegisterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val _detailState = MutableLiveData<Int>(0)
    val detailState: LiveData<Int> get() = _detailState

    private val _checkBoxState = MutableLiveData<Int>(1)
    val checkBoxState: LiveData<Int> get() = _checkBoxState

    //닉네임 입력 페이지
    var nickname = MutableLiveData<String>("")
    private val _nicknameEnable = MutableLiveData<Boolean>(false)
    val nicknameEnable: LiveData<Boolean> get() = _nicknameEnable

    private val _nicknameState = MutableLiveData<EditTextState>(EditTextState.NONE)
    val nicknameState: LiveData<EditTextState> get() = _nicknameState

    fun changeCheckBox(num: Int) {
        if (_checkBoxState.value != null) {
            if (num == 30) {
                if (_checkBoxState.value == 30) {
                    _checkBoxState.value = 1
                } else {
                    _checkBoxState.value = 30
                }
            } else {
                if (_checkBoxState.value!! % num == 0) {
                    _checkBoxState.value = _checkBoxState.value!! / num
                } else {
                    _checkBoxState.value = _checkBoxState.value!! * num
                }
            }
        } else {
            _checkBoxState.value = 1
        }
        Log.d("CheckBoxTest","${_checkBoxState.value}")
        checkButtonState()
    }

    fun changeRegisterState(registerType: RegisterType) {
        _registerState.value = registerType
        checkButtonState() //화면 넘어갈 때 체크해야 함
    }

    fun createRandomNickName() {
        //NickName 요청
        nickname.value = "랜덤아이디 생성"
        _nicknameEnable.value = true
        checkButtonState()
    }

    fun checkNickNameEnable() {
        val nick = nickname.value?:""
        if (nick.isNotEmpty() && nick.length >= 2) {
            //nick 중복 검사
            viewModelScope.launch {
                _nicknameEnable.value = true
            }
        } else {
            _nicknameEnable.value = false
        }

        nickname.value //를 레포에 전송하고, 받아오기
        checkButtonState()
    }

    fun clearNickName() {
        nickname.value = ""
        _nicknameEnable.value = false
        checkButtonState()
    }


    private fun checkButtonState() {
        when (_registerState.value) {
            RegisterType.SERVICE_AGREEMENT -> {
                _buttonState.value = (_checkBoxState.value == 30)
            }
            RegisterType.INFORMATION_INPUT1 -> {
                _buttonState.value = _nicknameEnable.value
            }
            RegisterType.INFORMATION_INPUT2 -> {

            }
            RegisterType.KEYWORD_RECOMMENDATION -> {

            }
            else -> {}
        }
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