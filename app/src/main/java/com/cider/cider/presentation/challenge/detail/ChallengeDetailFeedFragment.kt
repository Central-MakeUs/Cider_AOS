package com.cider.cider.presentation.challenge.detail

import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailFeedBinding
import com.cider.cider.databinding.FragmentChallengeDetailInfoBinding
import com.cider.cider.utils.binding.BindingFragment

class ChallengeDetailFeedFragment: BindingFragment<FragmentChallengeDetailFeedBinding>(R.layout.fragment_challenge_detail_feed) {

    override fun onBackPressed() {
        val parentNavController = requireActivity().findNavController(R.id.fl_main)
        parentNavController.popBackStack()
    }
}