package com.cider.cider.presentation.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.data.remote.model.RequestChallengeCreate
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.utils.FormDataUtil.getRealPathFromUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChallengeCreateViewModel @Inject constructor(
    private val repository: ChallengeRepository
):ViewModel() {
    private val _challengeSelect = MutableLiveData<Category>()
    val challengeSelect: LiveData<Category> get() = _challengeSelect

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

    private val _buttonState = MutableLiveData<Boolean>(false)
    val buttonState: LiveData<Boolean> get() = _buttonState

    private val _buttonState2 = MutableLiveData<Boolean>()
    val buttonState2: LiveData<Boolean> get() = _buttonState2

    fun changeChallenge(challenge: Category) {
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
        if (currentList.size > 0) {
            currentList[0] =imageCardModel
        } else {
            currentList.add(imageCardModel)
        }
        _successImageList.value = (currentList)
    }

    fun addImageFail(imageCardModel: ImageCardModel) {
        val currentList = _failImageList.value?.toMutableList() ?: mutableListOf()
        if (currentList.size > 0) {
            currentList[0] = imageCardModel
        } else {
            currentList.add(imageCardModel)
        }
        _failImageList.value = (currentList)
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

    fun checkButtonState() {
        _buttonState.value = (challengeTitle.value?.length!! >= 5 &&
        challengeIntroduction.value?.length!! >= 30 && challengeAuthentication.value?.length!! >= 5)
    }

    private fun checkButtonState2() {
        _buttonState2.value = _checkList.value?.all { it }
    }

    fun createChallenge(context: Context) {
        val contentResolver: ContentResolver = context.contentResolver
        val part1: MutableList<MultipartBody.Part> = mutableListOf()
        val part2: MutableList<MultipartBody.Part> = mutableListOf()

        _successImageList.value?.map {
            val filePath = getRealPathFromUri(contentResolver, it.uri)
            val imageFile = filePath?.let { it1 -> File(it1) }
            val requestFile: RequestBody? = imageFile?.asRequestBody("multipart/form-data".toMediaType())
            if (requestFile!=null) {
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("uploaded_file", imageFile.name, requestFile)
                part1.add(body)
            }
        }

        _failImageList.value?.map {
            val filePath = getRealPathFromUri(contentResolver, it.uri)
            val imageFile = filePath?.let { it1 -> File(it1) }
            val requestFile: RequestBody? = imageFile?.asRequestBody("multipart/form-data".toMediaType())
            if (requestFile!=null) {
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("uploaded_file", imageFile.name, requestFile)
                part2.add(body)
            }
        }
        viewModelScope.launch {
            repository.createChallenge(
                RequestChallengeCreate(
                    certifyMission = challengeAuthentication.value?:"",
                    challengeBranch = _challengeSelect.value?.api?:"",
                    challengeCapacity = _capacity.value?:3,
                    challengeInfo = challengeIntroduction.value?:"",
                    challengeName = challengeTitle.value?:"",
                    challengePeriod = challengePeriod.value?:1,
                    isPublic = false,
                    recruitPeriod = recruitmentPeriod.value?:1
                ),
                part1,
                part2
            )
        }

    }
}