package com.cider.cider.data.remote.datasource

import android.util.Log
import com.cider.cider.data.remote.api.RegisterApi
import com.cider.cider.data.remote.model.RequestLoginModel
import com.cider.cider.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiService: RegisterApi
): RegisterRepository {

    override suspend fun postLogin(header: String): Any {
        val n = apiService.postLogin(RequestLoginModel())
        Log.d("TEST API", "${n.isSuccessful}\n${n.code()}\n${n.errorBody()?.string()}")
        return "a"
    }

    override suspend fun getRandomNickName(): String {
        val n = apiService.getRandomNickname()

        Log.d("TEST API","${n.code()}\n${n.body()}\n${n.errorBody()?.string()}")

        return try {
            apiService.getRandomNickname().body()?.randomName?:""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}