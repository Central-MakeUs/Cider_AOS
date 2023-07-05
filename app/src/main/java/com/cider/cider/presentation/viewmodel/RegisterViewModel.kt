package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.type.*
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

    private val _genderState = MutableLiveData<Gender>()
    val genderState : LiveData<Gender> get() = _genderState

    private val _birth = MutableLiveData<Birth>()
    val birth: LiveData<Birth> get() = _birth

    private val _challengeState = MutableLiveData<ChallengeType>()
    val challengeState: LiveData<ChallengeType> get() = _challengeState

    private val _keywordState = MutableLiveData<List<KeyWord>>()
    val keywordState: LiveData<List<KeyWord>> get() = _keywordState

    init {
        _challengeState.value = ChallengeType()
        _birth.value = Birth(0,-1,0)
        _keywordState.value = arrayListOf(
            KeyWord("짠테크", false),
            KeyWord("앱테크", false),
            KeyWord("청년정책", false),
            KeyWord("소비생활", false),
            KeyWord("적금", false))    }


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
        nickname.value = "랜덤아이디생성"
        _nicknameEnable.value = true
        changeNickNameState(EditTextState.ACTIVE)
        checkButtonState()
    }

    fun checkNickNameEnable() {
        val nick = nickname.value?:""
        if (nick.isNotEmpty() && nick.length >= 2) {
            //nick 중복 검사
            viewModelScope.launch {
                _nicknameEnable.value = true
                changeNickNameState(EditTextState.ACTIVE) //중복 없을 때
                //_nicknameEnable.value = false
                //changeNickNameState(EditTextState.ERROR) //중복 있을 때

            }
        } else {
            _nicknameEnable.value = false
        }

        nickname.value //를 레포에 전송하고, 받아오기
        checkButtonState()
    }

    fun changeNickNameState(editTextState: EditTextState) {
        _nicknameState.value = editTextState
    }

    fun clearNickName() {
        nickname.value = ""
        _nicknameEnable.value = false
        checkButtonState()
    }

    fun changeGender(gender: Gender) {
        _genderState.value = gender
        checkButtonState()
    }

    fun changeBirth(birth: Birth) {
        _birth.value = birth
    }

    fun changeChallengeState(num: Int, state: Boolean) {
        val currentData = _challengeState.value

        if (currentData != null) {
            val updatedData = when (num) {
                0 -> currentData.copy(investing = state)
                1 -> currentData.copy(saving = state)
                2 -> currentData.copy(money_management = state)
                3 -> currentData.copy(financial_learning = state)
                else -> currentData
            }
            _challengeState.value = updatedData
        }
        Log.d("DataBindingTest","${_challengeState.value}")
    }

    fun changeKeyWordState(title: String) {
        //title 과 같은 아이템의 state를 !한다
        val currentData = _keywordState.value

        Log.d("KeyWordTest","? ${_keywordState.value}")
        if (currentData != null) {
            val updatedData = ArrayList<KeyWord>()
            currentData.forEach {
                if (it.title == title) {
                    updatedData.add(KeyWord(it.title, !it.state))
                } else {
                    updatedData.add(it)
                }
            }
            _keywordState.value = updatedData
        }
        Log.d("KeyWordTest","?? ${_keywordState.value}")
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
                if (_genderState.value != null) {
                    _buttonState.value = true
                } else {
                    _buttonState.value = false
                }
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