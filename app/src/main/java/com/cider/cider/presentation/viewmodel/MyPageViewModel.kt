package com.cider.cider.presentation.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.MyPageModel
import com.cider.cider.domain.model.ProfileModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.utils.FormDataUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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
        return repository.patchProfile(profileName.value ?: "")
    }

    suspend fun setProfileImage(context: Context) : Boolean{
        val contentResolver: ContentResolver = context.contentResolver
        val filePath = profileUri.value?.let {
            FormDataUtil.getRealPathFromUri(contentResolver,
                it
            )
        }
        val imageFile = filePath?.let { it1 -> File(it1) }
        val requestFile: RequestBody? = imageFile?.asRequestBody("multipart/form-data".toMediaType())
        if (requestFile!=null) {
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("uploaded_file", imageFile.name, requestFile)
            return repository.patchProfileImage(body)
        }
        return false
    }
}