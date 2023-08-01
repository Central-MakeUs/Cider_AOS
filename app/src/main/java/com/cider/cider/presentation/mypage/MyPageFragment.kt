package com.cider.cider.presentation.mypage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeListViewBinding
import com.cider.cider.databinding.FragmentMyPageBinding
import com.cider.cider.utils.binding.BindingFragmentNoNavi
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment: BindingFragmentNoNavi<FragmentMyPageBinding>(R.layout.fragment_my_page)  {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheet()
        setButton()
    }

    private fun setBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            // Handle other states if necessary
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.sectionCharacter.animate().scaleX(1-slideOffset * 0.34f).scaleY(1-slideOffset * 0.34f).setDuration(0).start();
            }

        })
    }

    private fun setButton() {
        binding.btnMyCertify.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_myCertifyFragment
            )
        }

        binding.btnInterest.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_challengeInterestFragment
            )
        }

        binding.btnChallengeCurrent.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_challengeMyFragment
            )
        }

        binding.ivProfileEdit.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_profileEditFragment
            )
        }
    }
}