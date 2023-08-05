package com.cider.cider.utils

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import java.io.File
import java.io.FileOutputStream

object FormDataUtil {

    fun getRealPathFromUri(contentResolver: ContentResolver, uri: Uri): String? {
        var filePath: String? = null

        // 기존에는 URI 스키마가 "content"인 경우에 대해 처리하는 방법을 보여드렸으나
        // SAF (Storage Access Framework)가 적용된 Android 10 (API 레벨 29) 이상에서는 다른 방식으로 처리해야 합니다.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(uri, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }
        } else {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))
                val fileName = "image_${System.currentTimeMillis()}.$extension"
                val file = File(context.cacheDir, fileName)
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                filePath = file.absolutePath
            }
        }

        return filePath
    }
}