package com.cider.cider.presentation.challenge.create

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateCheckBinding
import com.cider.cider.databinding.FragmentChallengeCreateCompleteBinding
import com.cider.cider.databinding.FragmentChallengeCreateDetailBinding
import com.cider.cider.databinding.FragmentChallengeCreateSelectBinding
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeCreateCheckFragment: BindingFragment<FragmentChallengeCreateCheckBinding>(R.layout.fragment_challenge_create_check) {

    private val viewModel: ChallengeCreateViewModel by hiltNavGraphViewModels(R.id.nav_challenge_create)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setButton()
    }

    private fun setButton() {
        binding.btnChallengeCheck.setOnClickListener {
            viewModel.createChallenge(requireContext())
            findNavController().navigate(
                R.id.action_challengeCreateCheckFragment_to_challengeCreateCompleteFragment
            )
        }
        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}