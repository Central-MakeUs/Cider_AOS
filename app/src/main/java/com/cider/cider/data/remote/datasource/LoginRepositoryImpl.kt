package com.cider.cider.data.remote.datasource

import android.util.Log
import com.cider.cider.App
import com.cider.cider.data.remote.api.LoginApi
import com.cider.cider.data.remote.model.RequestLoginModel
import com.cider.cider.data.remote.model.RequestMember
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
            val data = apiService.postLogin(header, RequestLoginModel())
            Log.e("TEST 로그인"," POST Login : $header\n$data")
            data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun patchMember(accessToken: String, param: RequestMember): Boolean {
        return try {
            val data = apiService.patchMember(accessToken, param)
            Log.e("TEST 로그인","$data")
            data.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
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
                    else -> false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun postLogout():Boolean {
        return try {
            val data = apiService.postLogout(App.prefs.getString("refreshToken","")) == "로그아웃이 완료되었습니다."
            data
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun postSignOut(): Boolean {
        return try {
            val data = apiService.postSignOut(App.prefs.getString("refreshToken",""))
            data.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}