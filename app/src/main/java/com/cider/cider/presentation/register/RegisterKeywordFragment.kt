package com.cider.cider.presentation.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterKeywordBinding
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
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
        setObserver()
    }

    private fun setChallenge() {

    }
    private fun setObserver() {
        viewModel.challengeState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {

            }
        }
    }
}