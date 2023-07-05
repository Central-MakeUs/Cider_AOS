package com.cider.cider.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterKeywordBinding
import com.cider.cider.domain.type.KeyWord
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.adapter.KeyWordAdapter
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.launch

class RegisterKeywordFragment
    :BindingFragment<FragmentRegisterKeywordBinding>(R.layout.fragment_register_keyword) {

    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.changeRegisterState(RegisterType.KEYWORD_RECOMMENDATION)

        setChallenge()
        setKeyword()
        setObserver()
    }

    private fun setKeyword() {
        val keywordAdapter = KeyWordAdapter(viewModel)

        keywordAdapter.submitList(viewModel.keywordState.value)

        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
        }.let {
            binding.rvKeyword.layoutManager = it
            binding.rvKeyword.adapter = keywordAdapter
        }

        viewModel.keywordState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                keywordAdapter.submitList(it)
            }
        }
    }
    private fun setChallenge() {
        binding.btnChallenge1.setOnClickListener {
            viewModel.changeChallengeState(0, !binding.btnChallenge1.isSelected)
        }
        binding.btnChallenge2.setOnClickListener {
            viewModel.changeChallengeState(1, !binding.btnChallenge2.isSelected)
        }
        binding.btnChallenge3.setOnClickListener {
            viewModel.changeChallengeState(2, !binding.btnChallenge3.isSelected)
        }
        binding.btnChallenge4.setOnClickListener {
            viewModel.changeChallengeState(3, !binding.btnChallenge4.isSelected)
        }
    }
    private fun setObserver() {
        viewModel.challengeState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                binding.btnChallenge1.isSelected = it.investing
                binding.btnChallenge2.isSelected = it.saving
                binding.btnChallenge3.isSelected = it.money_management
                binding.btnChallenge4.isSelected = it.financial_learning
            }
        }
    }
}