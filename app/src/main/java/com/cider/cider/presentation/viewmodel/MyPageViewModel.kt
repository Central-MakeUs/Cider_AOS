package com.cider.cider.presentation.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.MyPageModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.utils.Constant.IMAGE_MAX_SIZE
import com.cider.cider.utils.FormDataUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
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
        val imageFile = filePath?.let { it -> File(it) }
        // 압축 비율 계산
        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, profileUri.value)

        val maxFileSizeKB = IMAGE_MAX_SIZE
        val quality = if ((maxFileSizeKB * 100) / (bitmap.byteCount / 1024) >= 100) 100 else (maxFileSizeKB * 100) / (bitmap.byteCount / 1024)

        bitmap.let { compressedBitmap ->
            // Bitmap을 압축
            val compressedFile = File(context.cacheDir, imageFile?.name.toString())
            val outputStream = FileOutputStream(compressedFile)
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            val requestFile: RequestBody = compressedFile.asRequestBody("multipart/form-data".toMediaType())
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("profileImage", compressedFile.name, requestFile)
            return repository.patchProfileImage(body)
        }
    }
}