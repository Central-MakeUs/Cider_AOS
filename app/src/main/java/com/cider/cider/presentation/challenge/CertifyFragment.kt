package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCertifyBinding
import com.cider.cider.utils.binding.BindingFragment

class CertifyFragment: BindingFragment<FragmentChallengeCertifyBinding>(R.layout.fragment_challenge_certify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButton()
    }

    private fun setButton() {
        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

}