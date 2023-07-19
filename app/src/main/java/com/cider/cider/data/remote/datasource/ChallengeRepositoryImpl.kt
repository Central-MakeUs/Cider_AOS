package com.cider.cider.data.remote.datasource

import com.cider.cider.data.remote.api.ChallengeApi
import com.cider.cider.domain.repository.ChallengeReposiory
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val apiService: ChallengeApi
): ChallengeReposiory {
}