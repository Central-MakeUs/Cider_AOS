package com.cider.cider.presentation.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterCompletionBinding
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment

class RegisterCompletionFragment
    :BindingFragment<FragmentRegisterCompletionBinding>(R.layout.fragment_register_completion) {

    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.changeRegisterState(RegisterType.COMPLETION)
    }
}