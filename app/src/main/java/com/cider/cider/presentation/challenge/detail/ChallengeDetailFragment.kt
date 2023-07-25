package com.cider.cider.presentation.challenge.detail

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailBinding
import com.cider.cider.utils.binding.BindingFragment

class ChallengeDetailFragment: BindingFragment<FragmentChallengeDetailBinding>(R.layout.fragment_challenge_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun getPeekHeight(): Int {
        // 60dp를 픽셀로 변환하여 반환
        val peekHeightInDp = 60
        val metrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, peekHeightInDp.toFloat(), metrics).toInt()
    }
}