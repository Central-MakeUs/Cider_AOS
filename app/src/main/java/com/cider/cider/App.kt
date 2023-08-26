package com.cider.cider

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.cider.cider.utils.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()

        //Kakao SDK 초기화
        KakaoSdk.init(this, resources.getString(R.string.APP_KEY))
        prefs = PreferenceUtil(applicationContext)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}