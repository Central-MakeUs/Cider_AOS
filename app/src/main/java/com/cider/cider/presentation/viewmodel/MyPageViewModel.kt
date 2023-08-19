package com.cider.cider.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.MyPageModel
import com.cider.cider.domain.model.ProfileModel
import com.cider.cider.domain.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repository: ChallengeRepository
):ViewModel() {
    private val _myPageData = MutableLiveData<MyPageModel>()
    val myPageModel: LiveData<MyPageModel> get() = _myPageData

    val profileName = MutableLiveData<String>("")
    val profileUri = MutableLiveData<Uri>()
    init {
        getMyPageData()
    }

    fun getMyPageData() {
        viewModelScope.launch {
            _myPageData.value = repository.getMyPage()
            getProfile()
        }
    }

    private fun getProfile() {
        profileName.value = _myPageData.value?.name
        profileUri.value = _myPageData.value?.profileUri
    }

    suspend fun setProfile(): Boolean {
        return if (profileName.value == _myPageData.value?.name) {
            repository.patchProfile(profileName.value ?: "")
        } else if (profileUri.value == _myPageData.value?.profileUri) {
            repository.patchProfile(profileName.value ?: "")
        } else {
            false
        }
    }
}