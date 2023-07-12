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
        return "a"
    }

    override suspend fun getRandomNickName(): String {
        return try {
            apiService.getRandomNickname().body()?.randomName?:""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun getNickNameExist(nickname: String): Boolean {
        return try {
            apiService.getNicknameExist(nickname).body()?.message == "사용할 수 있는 닉네임입니다."
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}