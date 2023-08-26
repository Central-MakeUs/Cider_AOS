package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.App
import com.cider.cider.data.remote.model.RequestMember
import com.cider.cider.domain.repository.LoginRepository
import com.cider.cider.domain.type.*
import com.cider.cider.domain.type.challenge.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
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

    private val _nicknameState = MutableLiveData<EditTextState>(EditTextState.NONE)
    val nicknameState: LiveData<EditTextState> get() = _nicknameState

    private val _genderState = MutableLiveData<Gender>()
    val genderState : LiveData<Gender> get() = _genderState

    private val _birth = MutableLiveData<Birth>(Birth(0,-1,0))
    val birth: LiveData<Birth> get() = _birth

    private val _challengeState = MutableLiveData<ChallengeButtonState>(ChallengeButtonState())
    val challengeState: LiveData<ChallengeButtonState> get() = _challengeState

    private var accessToken: String = ""
    private var refreshToken: String = ""
    suspend fun loginFirst(header: String): Boolean {
        val data = repository.postLogin(header)
        accessToken = data?.body()?.accessToken?:""
        refreshToken = data?.body()?.refreshToken?:""
        Log.d("TEST login","${data?.body()}")
        return data?.body()?.isUpdatedMember == true //회원가입 했으면 true, 안했으면 false
    }

    fun setToken() {
        App.prefs.setString("accessToken", accessToken)
        App.prefs.setString("refreshToken", refreshToken)
    }

    suspend fun setMember(): Boolean {
        var interest:String = ""
        _challengeState.value.let {
            if (it?.investing == true ) {
                interest += ("T,")
            }
            if (it?.money_management == true ) {
                interest += ("M,")
            }
            if (it?.financial_learning == true ) {
                interest += ("L,")
            }
            if (it?.saving == true ) {
                interest += ("C,")
            }
        }
        if (interest.isNotEmpty()) {
            interest = interest.substring(0,interest.length-1)
        }

        val param = RequestMember(
            memberName = nickname.value?:"",
            memberBirth = "${_birth.value?.year}-${(_birth.value?.month ?:0) + 1}-${_birth.value?.day}",
            memberGender = (_genderState.value?.api ?:"M"),
            interestChallenge = interest
        )

        return try {
            if (repository.patchMember(accessToken, param)) {
                App.prefs.setString("accessToken", accessToken)
                App.prefs.setString("refreshToken", refreshToken)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 앱 실행 시 로그인 시도
     * accessToken 을 가지고 me 호출
     * repository 에서 200 일 경우, 로그인 성공
     * 200 이외의 400 일 경우 로그인 실패로 간주
     *
     * 402 의 경우 토큰 만료 -> 만료 시 재로그인해야하기에 로그인 화면으로 넘어가면 됨
     */
    suspend fun login(): Boolean {
        return if (App.prefs.getString("accessToken","").isNotEmpty()) repository.getLoginMe() else false
    }

    suspend fun logout(): Boolean {
        return repository.postLogout()
    }

    suspend fun signOut(): Boolean {
        return repository.postSignOut()
    }


    fun getRegisterData(name: String?, date: Int?, gender: Gender?) {
        if (name != null) nickname.value = name?:""
        if (date != null) _birth.value = Birth(0, date/100-1, date%100)
        if (gender != null) _genderState.value = gender?:Gender.MALE
    }

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
        checkButtonState()
    }

    fun changeRegisterState(registerType: RegisterType) {
        _registerState.value = registerType
        checkButtonState() //화면 넘어갈 때 체크해야 함
        when (registerType) {
            RegisterType.INFORMATION_INPUT1 -> {
                viewModelScope.launch(Dispatchers.Main) { checkNickNameEnable() }
            }
            RegisterType.INFORMATION_INPUT2 -> {

            }
            else -> {}
        }
    } //화면 넘어갈 때 체크

    fun createRandomNickName() {
        viewModelScope.launch(Dispatchers.Main) {
            nickname.value = repository.getRandomNickName()
            changeNickNameState(EditTextState.ENABLE)
        }
    }

    suspend fun checkNickNameEnable() {
        val nick = nickname.value?:""
        if (nick.isNotEmpty() && nick.length >= 2) {
            if (repository.getNickNameExist(nick)) {
                changeNickNameState(EditTextState.ENABLE) //중복 없을 때
            }
            else {
                changeNickNameState(EditTextState.ERROR_DUPLICATION) //중복 있을 때
            }
        } else {
            changeNickNameState(EditTextState.ERROR_MIN)
        }
        nickname.value //를 레포에 전송하고, 받아오기
    }

    fun changeNickNameState(editTextState: EditTextState) {
        _nicknameState.value = editTextState
        checkButtonState()
    }

    fun clearNickName() {
        nickname.value = ""
        changeNickNameState(EditTextState.NONE)
    }

    fun changeGender(gender: Gender) {
        _genderState.value = gender
        checkButtonState()
    }

    fun changeBirth(birth: Birth) {
        _birth.value = birth
        checkButtonState()
    }

    fun changeChallengeState(challenge: Category) {
        val currentState = _challengeState.value
        val updateState = when (challenge) {
            Category.INVESTING -> { currentState?.copy(investing = !currentState.investing) }
            Category.FINANCIAL_LEARNING -> { currentState?.copy(financial_learning = !currentState.financial_learning)}
            Category.MONEY_MANAGEMENT -> { currentState?.copy(money_management = !currentState.money_management)}
            Category.SAVING -> { currentState?.copy(saving = !currentState.saving)}
        }
        _challengeState.value = updateState?:ChallengeButtonState()
    }

    private fun checkButtonState() {
        when (_registerState.value) {
            RegisterType.SERVICE_AGREEMENT -> {
                _buttonState.value = (_checkBoxState.value == 30)
                _detailState.value = 0
            }
            RegisterType.INFORMATION_INPUT1 -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _buttonState.value = (_nicknameState.value == EditTextState.ENABLE)
                }
            }
            RegisterType.INFORMATION_INPUT2 -> {
                _buttonState.value = (_genderState.value != null) &&
                        if (_birth.value?.year == 0) false
                        else _birth.value?.hasPassed14Years()?:false
            }
            RegisterType.COMPLETION -> {
                _buttonState.value = true
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
    }
}