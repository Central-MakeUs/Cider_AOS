package com.cider.cider.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.cider.cider.domain.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCertifyViewModel @Inject constructor(
    private val repository: ChallengeRepository
): ViewModel()
{
}