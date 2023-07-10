package com.cider.cider.presentation.challenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeHomeBinding
import com.cider.cider.presentation.register.LoginFragment
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeHomeFragment: BindingFragment<FragmentChallengeHomeBinding>(R.layout.fragment_challenge_home) {

    private lateinit var callback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setButton() {
        binding.btnTest.setOnClickListener {
            view?.findNavController()?.navigate(
                R.id.action_challengeHomeFragment_to_challengeCreateFragment
            )
        }
    }

    private fun onBackPressed() {
        requireActivity().finish()
    }
}