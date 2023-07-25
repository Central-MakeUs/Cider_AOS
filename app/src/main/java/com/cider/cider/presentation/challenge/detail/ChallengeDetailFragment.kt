package com.cider.cider.presentation.challenge.detail

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailBinding
import com.cider.cider.presentation.adapter.ChallengeDetailViewPagerAdapter
import com.cider.cider.utils.binding.BindingFragment
import com.google.android.material.tabs.TabLayoutMediator

class ChallengeDetailFragment: BindingFragment<FragmentChallengeDetailBinding>(R.layout.fragment_challenge_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheet()
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

    private fun getPeekHeight(): Int {
        // 60dp를 픽셀로 변환하여 반환
        val peekHeightInDp = 60
        val metrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, peekHeightInDp.toFloat(), metrics).toInt()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}