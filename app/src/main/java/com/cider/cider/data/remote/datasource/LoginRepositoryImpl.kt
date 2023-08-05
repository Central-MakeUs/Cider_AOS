package com.cider.cider.data.remote.datasource

import android.util.Log
import com.cider.cider.App
import com.cider.cider.data.remote.api.LoginApi
import com.cider.cider.data.remote.model.RequestLoginModel
import com.cider.cider.data.remote.model.ResponseLoginModel
import com.cider.cider.data.remote.model.ResponseMe
import com.cider.cider.domain.repository.LoginRepository
import com.cider.cider.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: LoginApi
): LoginRepository {

    override suspend fun postLogin(header: String): Response<ResponseLoginModel>? {
        return try {
            apiService.postLogin(header, RequestLoginModel())
        } catch (e: Exception) {
            e.printStackTrace()
            null
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
            val accessToken = App.prefs.getString("accessToken","")
            val data = apiService.getMe(accessToken)
            data.run {
                when (code()) {
                    200 -> true
                    401 -> {
                        App.prefs.setString("refreshToken",apiService.getMe(App.prefs.getString("refreshToken", "")).headers().toString())
                        //TODO(Refresh 만료 상황 필요)
                        true
                    }
                    else -> false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun postLogout() {
        TODO("Not yet implemented")
    }
}