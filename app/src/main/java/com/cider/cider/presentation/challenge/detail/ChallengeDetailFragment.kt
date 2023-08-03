package com.cider.cider.presentation.challenge.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailBinding
import com.cider.cider.presentation.adapter.ChallengeDetailViewPagerAdapter
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
        setBanner()
    }

    private fun setBanner() {
    }

    private fun setBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.appbar.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.btn_blue))
                        binding.btnToolbarBack.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                        binding.btnToolbarIcon.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                        binding.background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.btn_blue))
                        binding.viewProfile.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.appbar.setBackgroundColor(Color.WHITE)
                        binding.btnToolbarBack.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
                        binding.btnToolbarIcon.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
                        binding.background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                        binding.viewProfile.visibility = View.GONE
                    }
                    // Handle other states if necessary
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.viewProfile.animate().scaleX(1-slideOffset).scaleY(1-slideOffset).setDuration(0).start();
            }

        })
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