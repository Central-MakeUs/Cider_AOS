package com.cider.cider.data.remote

import com.cider.cider.data.remote.api.RegisterApi
import com.cider.cider.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiService: RegisterApi
): RegisterRepository {

}