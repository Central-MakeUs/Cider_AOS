package com.cider.cider.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.MyPageModel
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

    init {
        getMyPageData()
    }

    private fun getMyPageData() {
        viewModelScope.launch {
            _myPageData.value = repository.getMyPage()
        }
    }
}