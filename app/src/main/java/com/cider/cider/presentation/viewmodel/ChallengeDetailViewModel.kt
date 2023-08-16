package com.cider.cider.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.R
import com.cider.cider.domain.model.CertifyDetailModel
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeDetailModel
import com.cider.cider.domain.model.ChallengeModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.utils.getResourceUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val repository: ChallengeRepository
): ViewModel() {

    private val _detail = MutableLiveData<ChallengeDetailModel>()
    val detail: LiveData<ChallengeDetailModel> get() = _detail

    private val _certify = MutableLiveData<List<CertifyDetailModel>>()
    val certify: LiveData<List<CertifyDetailModel>> get() = _certify

    private val _imageList: MutableLiveData<List<ImageCardModel>> = MutableLiveData()
    val imageList: LiveData<List<ImageCardModel>> get() = _imageList

    suspend fun getDetail(id: Int) {
        _detail.value = repository.getChallengeDetail(id)
        getCertify(id, Filter.LATEST)
    }

    fun changeFeedLike(targetId: Int) {

    }

    fun getCertify(id: Int, filter: Filter) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getCertifyDetail(id,filter)

            if (data?.isSuccessful == true) {
                _certify.value = data.body()?.simpleCertifyResponseDtoList?.map {
                    CertifyDetailModel(
                        certifyContent = it.certifyContent,
                        certifyLike = it.certifyLike,
                        certifyName = it.certifyName,
                        createdDate = it.createdDate,
                        isLike = it.isLike,
                        memberLevel = it.simpleMemberResponseDto.memberLevel,
                        memberName = it.simpleMemberResponseDto.memberName,
                        profilePath = Uri.parse(it.simpleMemberResponseDto.profilePath),
                        certifyImage = it.certifyImageUrl.let { it1 -> Uri.parse(it1) }
                    )
                }

                _imageList.value = data.body()?.certifyImageUrlList?.map {
                    ImageCardModel(Uri.parse(it))
                }

            }
        }
    }


    fun changeLike(isCheck: Boolean) {
        viewModelScope.launch {
            if (isCheck) {
                _detail.value?.challengeId?.let { repository.deleteChallengeLike(it)}
            } else {
                _detail.value?.challengeId?.let { repository.postChallengeLike(it) }
            }
        }
    }
}