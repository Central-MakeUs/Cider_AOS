package com.cider.cider.di

import com.cider.cider.App
import com.cider.cider.data.remote.api.ChallengeApi
import com.cider.cider.data.remote.api.LoginApi
import com.cider.cider.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRegisterApiService(): LoginApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChallengeApiService() : ChallengeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(AppInterceptor()))
            .build()
            .create(ChallengeApi::class.java)
    }

    class AppInterceptor: Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val accessToken = App.prefs.getString("accessToken","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJhdWQiOiIyNCIsImlhdCI6MTY4OTc0Njg5MCwiZXhwIjoxNzIxMjgyODkwfQ.E6Mw7qtJIY4Pa9hYHDQNxT_OMZtimW_Xap3i2v2tAUf-wyVPYo8h94mcwiRgCDG7VT0ws_bhWwGWm-ZxEVVfQQ")
            val newRequest = request().newBuilder()
                .addHeader("Authorization",accessToken)
                .build()
            proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun okHttpClient(interceptor: AppInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
}