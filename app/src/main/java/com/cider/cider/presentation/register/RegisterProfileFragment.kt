package com.cider.cider.presentation.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterProfileBinding
import com.cider.cider.domain.type.Gender
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterProfileFragment
    :BindingFragment<FragmentRegisterProfileBinding>(R.layout.fragment_register_profile) {

    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.changeRegisterState(RegisterType.INFORMATION_INPUT2)

        setButton()
        setObserver()
    }

    private fun setButton() {
        binding.btnProfileMale.setOnClickListener {
            viewModel.changeGender(Gender.MALE)
        }

        binding.btnProfileFemale.setOnClickListener {
            viewModel.changeGender(Gender.FEMALE)
        }
    }

    private fun setObserver () {
        viewModel.genderState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                if (it == Gender.MALE) {
                    binding.btnProfileMale.isSelected = true
                    binding.btnProfileFemale.isSelected = false
                } else {
                    binding.btnProfileMale.isSelected = false
                    binding.btnProfileFemale.isSelected = true
                }
            }
        }
    }
    private fun setBirth() {

    }
}