package com.cider.cider.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentInterestChallengeBinding
import com.cider.cider.utils.binding.BindingFragment

class ChallengeInterestFragment: BindingFragment<FragmentInterestChallengeBinding>(R.layout.fragment_interest_challenge) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setButton()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "관심 챌린지"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
        binding.toolbar.tvTotal.text = "총 n개"
    }

    private fun setButton() {
        binding.btnChallengeLook.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeInterestFragment_to_challengeCreateFragment
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}