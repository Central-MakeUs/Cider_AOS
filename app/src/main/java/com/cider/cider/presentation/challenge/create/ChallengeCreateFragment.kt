package com.cider.cider.presentation.challenge.create

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateBinding
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeCreateFragment: BindingFragment<FragmentChallengeCreateBinding>(R.layout.fragment_challenge_create) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("TEST BackPress","Create")
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}