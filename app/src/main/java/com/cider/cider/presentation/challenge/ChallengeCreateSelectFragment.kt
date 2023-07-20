package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateSelectBinding
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.presentation.dialog.ChallengeExitDialog
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeCreateSelectFragment: BindingFragment<FragmentChallengeCreateSelectBinding>(R.layout.fragment_challenge_create_select) {

    private val viewModel: ChallengeCreateViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.challenge = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setButton()
    }

    private fun setButton() {
        binding.btnInvesting.setOnClickListener {
            viewModel.changeChallenge(Challenge.INVESTING)
            nextFragment()
        }
        binding.btnFinancialLearning.setOnClickListener {
            viewModel.changeChallenge(Challenge.FINANCIAL_LEARNING)
            nextFragment()
        }
        binding.btnSaving.setOnClickListener {
            viewModel.changeChallenge(Challenge.SAVING)
            nextFragment()
        }
        binding.btnMoneyManagement.setOnClickListener {
            viewModel.changeChallenge(Challenge.MONEY_MANAGEMENT)
            nextFragment()
        }
        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun nextFragment() {
        findNavController().navigate(
            R.id.action_challengeCreateSelectFragment_to_challengeCreateDetailFragment
        )
    }

    override fun onBackPressed() {
        if (viewModel.checkChallengeInput()) {
            showDialog()
        } else {
            val parentNavController = requireActivity().findNavController(R.id.fl_main)
            parentNavController.popBackStack()
        }
    }

    private fun showDialog() {
        val dialog = ChallengeExitDialog()
        dialog.show(parentFragmentManager,null)
    }
}