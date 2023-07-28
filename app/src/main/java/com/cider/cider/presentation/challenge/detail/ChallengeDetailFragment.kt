package com.cider.cider.presentation.challenge.detail

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailBinding
import com.cider.cider.presentation.adapter.ChallengeDetailViewPagerAdapter
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.google.android.material.tabs.TabLayoutMediator

class ChallengeDetailFragment: BindingFragment<FragmentChallengeDetailBinding>(R.layout.fragment_challenge_detail) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()
    init {
        Log.d("TEST Lifecycle","ChallengeDetailFragment")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBehavior()
        setBottomSheet()
    }

    private fun setBehavior() {

    }

    private fun setBottomSheet() {
        val pagerAdapter = ChallengeDetailViewPagerAdapter(requireActivity())

        pagerAdapter.addFragment(ChallengeDetailInfoFragment())
        pagerAdapter.addFragment(ChallengeDetailFeedFragment())
        Log.d("TEST detail","??")
        binding.vpChallenge.adapter = pagerAdapter

        val tabTitle = listOf<String>("챌린지 정보","피드")
        TabLayoutMediator(binding.tabLayout, binding.vpChallenge) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}