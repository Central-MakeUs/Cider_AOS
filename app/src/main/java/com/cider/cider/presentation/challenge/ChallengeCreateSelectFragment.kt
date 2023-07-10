package com.cider.cider.presentation.challenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateBinding
import com.cider.cider.databinding.FragmentChallengeCreateSelectBinding
import com.cider.cider.databinding.FragmentChallengeHomeBinding
import com.cider.cider.presentation.register.LoginFragment
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeCreateSelectFragment: BindingFragment<FragmentChallengeCreateSelectBinding>(R.layout.fragment_challenge_create_select) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
    }

    private fun setButton() {
        binding.btnInvesting.setOnClickListener {
            nextFragment()
        }
        binding.btnFinancialLearning.setOnClickListener {
            nextFragment()
        }
        binding.btnSaving.setOnClickListener {
            nextFragment()
        }
        binding.btnMoneyManagement.setOnClickListener {
            nextFragment()
        }
    }

    private fun nextFragment() {
        val navController = view?.findNavController()

        navController?.navigate(
            R.id.action_challengeCreateSelectFragment_to_challengeCreateDetailFragment
        )
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
        Log.d("TEST BackPress","CreateSelect")
    }
}