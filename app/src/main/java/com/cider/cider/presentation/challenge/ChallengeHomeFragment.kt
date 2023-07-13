package com.cider.cider.presentation.challenge

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeHomeBinding
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeHomeFragment: BindingFragment<FragmentChallengeHomeBinding>(R.layout.fragment_challenge_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBar()
        setButton()
    }

    private fun setAppBar() {
        binding.toolbar.tvToolbarTitle.text = "챌린지"
        binding.toolbar.btnToolbarBack.visibility = View.INVISIBLE
        binding.toolbar.btnToolbarIcon1.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.line_my_24))

        binding.toolbarPopularChallenge.toolbarTitle.text = "인기 챌린지"
        binding.toolbarOfficialChallenge.toolbarTitle.text = "바로 참여 가능! 공식 챌린지"
        binding.toolbarCategoryChallenge.toolbarTitle.text = "카테고리"
        binding.toolbarCategoryChallenge.tvMore.text = "전체 챌린지 보기"
    }

    private fun setRecyclerView () {

    }

    private fun setPopularChallenge() {

    }

    private fun setButton() {
        binding.btnTest.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeCreateFragment
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        requireActivity().finish()
    }
}