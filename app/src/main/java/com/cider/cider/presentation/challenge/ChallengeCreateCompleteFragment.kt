package com.cider.cider.presentation.challenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateDetailBinding
import com.cider.cider.databinding.FragmentChallengeCreateSelectBinding
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeCreateCompleteFragment: BindingFragment<FragmentChallengeCreateDetailBinding>(R.layout.fragment_challenge_create_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
    }

    private fun setButton() {

    }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun onBackPressed() {
        Log.d("TEST BackPress","CreateSDetail")
    }
}