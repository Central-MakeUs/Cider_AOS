package com.cider.cider.presentation.challenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateDetailBinding
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeCreateDetailFragment: BindingFragment<FragmentChallengeCreateDetailBinding>(R.layout.fragment_challenge_create_detail) {

    private val viewModel: ChallengeCreateViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.challenge = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setButton()
        setEditText()
    }

    private fun setEditText() {
        binding.etChallengeTitle.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeTitleAlert.visibility =
                    if ((viewModel.challengeTitle.value?.length ?: 0) < 5) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeIntroduction.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeIntroductionAlert.visibility =
                    if ((viewModel.challengeIntroduction.value?.length ?: 0) < 30) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeAuthentication.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeAuthentication.visibility =
                    if ((viewModel.challengeAuthentication.value?.length ?: 0) < 5) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeAuthentication.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etChallengeAuthentication.clearFocus()
                hideKeyBoard()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setButton() {
        binding.btnChallengeCreate.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeCreateDetailFragment_to_challengeCreateCompleteFragment
            )
        }

        binding.etChallengeCapacity.setOnClickListener {
            hideKeyBoard()
            //TODO(Bottom Sheet)
        }

        binding.etChallengeChallengePeriod.setOnClickListener {
            hideKeyBoard()
            //TODO(Bottom Sheet)
        }

        binding.etChallengeRecruitmentPeriod.setOnClickListener {
            hideKeyBoard()
            //TODO(Bottom Sheet)
        }


        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    private fun hideKeyBoard() {
        binding.etChallengeIntroduction.clearFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etChallengeAuthentication.windowToken, 0)
    }
}