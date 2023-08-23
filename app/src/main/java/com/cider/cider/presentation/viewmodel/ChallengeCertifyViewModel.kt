package com.cider.cider.presentation.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.data.remote.model.RequestCertify
import com.cider.cider.domain.model.ChallengeListModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.utils.Constant
import com.cider.cider.utils.FormDataUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ChallengeCertifyViewModel @Inject constructor(
    private val repository: ChallengeRepository,
): ViewModel() {

    val title = MutableLiveData<String>("")
    val content = MutableLiveData<String>("")

    private val _certifyImageList: MutableLiveData<List<ImageCardModel>> = MutableLiveData()
    val certifyImageList: LiveData<List<ImageCardModel>> get() = _certifyImageList

    private val _buttonState = MutableLiveData<Boolean>(false)
    val buttonState: LiveData<Boolean> get() = _buttonState

    fun clearTitle() {
        title.value = ""
    }

    fun addImage(imageCardModel: ImageCardModel) {
        val currentList = _certifyImageList.value?.toMutableList() ?: mutableListOf()
        if (currentList.size > 0) {
            currentList[0] =imageCardModel
        } else {
            currentList.add(imageCardModel)
        }
        _certifyImageList.value = (currentList)
        checkButtonState()
    }

    fun checkButtonState() {
        _buttonState.value = (title.value?.length!! >= 1 &&
                content.value?.length!! >= 1 &&
                !_certifyImageList.value.isNullOrEmpty())
    }

    suspend fun getChallengeList(): List<ChallengeListModel>? {
        return repository.getChallengeParticipate()
    }

    suspend fun setCertify(context: Context, id: Int) : Boolean{
        val contentResolver: ContentResolver = context.contentResolver
        val part: MutableList<MultipartBody.Part> = mutableListOf()

        _certifyImageList.value?.map {
            val filePath = FormDataUtil.getRealPathFromUri(contentResolver, it.uri)
            val imageFile = filePath?.let { it1 -> File(it1) }
            // 압축 비율 계산
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it.uri)

            val maxFileSizeKB = Constant.IMAGE_MAX_SIZE
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
                    MultipartBody.Part.createFormData("certifyImages", imageFile?.name, requestFile)
                part.add(body)
            }
        }

        return repository.postChallengeCertify(
            RequestCertify(id, title.value?:"", content.value?:""),
            part
        )
    }

}