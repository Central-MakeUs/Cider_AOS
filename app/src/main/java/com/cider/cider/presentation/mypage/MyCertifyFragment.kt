package com.cider.cider.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentMyCertifyBinding
import com.cider.cider.utils.binding.BindingFragment

class MyCertifyFragment: BindingFragment<FragmentMyCertifyBinding>(R.layout.fragment_my_certify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setButton()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "나의 인증글"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
        binding.toolbar.tvTotal.text = "총 n개"
    }

    private fun setButton() {
        binding.btnChallengeLook.setOnClickListener {
            findNavController().navigate(
                R.id.action_myCertifyFragment_to_challengeHomeFragment
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}