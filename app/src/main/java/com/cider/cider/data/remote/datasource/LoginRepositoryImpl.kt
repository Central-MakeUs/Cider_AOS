package com.cider.cider.data.remote.datasource

import android.util.Log
import com.cider.cider.App
import com.cider.cider.data.remote.api.LoginApi
import com.cider.cider.data.remote.model.RequestLoginModel
import com.cider.cider.data.remote.model.ResponseMe
import com.cider.cider.domain.repository.LoginRepository
import com.cider.cider.utils.Constants
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: LoginApi
): LoginRepository {

    override suspend fun postLoginFirst(header: String): Any {
        val data = apiService.postLogin(header,RequestLoginModel())
        Log.d("TEST Login","${data}")
        return data
    }

    override suspend fun postLogin() {
        val accessToken = App.prefs.getString("accessToken","")
        if (accessToken.isNotEmpty()) {
            try {
                val data = apiService.postLogin(accessToken, RequestLoginModel())
                Log.d(
                    "Test Login2",
                    "${data}\n${data.message()}\n${data.body()}\n${
                        data.errorBody()?.string()
                    }\n${data.isSuccessful}"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            App.prefs.setString("accessToken",Constants.TEST_API_KEY)
        }
    }



    override suspend fun getRandomNickName(): String {
        return try {
            val data = apiService.getRandomNickname()
            data.run {
                when( code() ){
                    200 -> {data.body()?.randomName?:""}
                    400 -> {data.errorBody()?.string()?:"" }
                    else -> {""}
                }
            }
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

    override suspend fun getLoginMe(): Boolean {
        return try {
            val data = apiService.getMe()
            data.run {
                when (code()) {
                    200 -> true
                    else -> false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getMe(): ResponseMe? {
        return apiService.getMe().body()
    }

    override suspend fun postLogout() {
        TODO("Not yet implemented")
    }
}