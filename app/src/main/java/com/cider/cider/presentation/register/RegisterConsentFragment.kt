package com.cider.cider.presentation.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterConsentBinding
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterConsentFragment
    :BindingFragment<FragmentRegisterConsentBinding>(R.layout.fragment_register_consent) {

    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register = viewModel
        viewModel.changeRegisterState(RegisterType.SERVICE_AGREEMENT)

        setButton()
        setCheckBox()
        setObserver()
    }

    private fun setButton() {
        binding.btnConsent2.setOnClickListener {
            viewModel.setTermDetail(1)
            if (viewModel.detailState.value == 1) {
                binding.sv1.visibility = View.VISIBLE
                binding.sv2.visibility = View.GONE

                binding.btnConsent2.text = "접기"
                binding.btnConsent3.text = "자세히 보기"

                binding.layoutService.layoutParams.height = resources.getDimensionPixelSize(R.dimen.register_service_0dp)
            } else {
                binding.sv1.visibility = View.GONE
                binding.btnConsent2.text = "자세히 보기"
                binding.layoutService.layoutParams.height = LayoutParams.WRAP_CONTENT
            }
        }

        binding.btnConsent3.setOnClickListener {
            viewModel.setTermDetail(2)
            if (viewModel.detailState.value == 2) {
                binding.sv2.visibility = View.VISIBLE
                binding.sv1.visibility = View.GONE

                binding.btnConsent3.text = "접기"
                binding.btnConsent2.text = "자세히 보기"
                binding.layoutService.layoutParams.height = resources.getDimensionPixelSize(R.dimen.register_service_0dp)
            } else {
                binding.sv2.visibility = View.GONE
                binding.btnConsent3.text = "자세히 보기"
                binding.layoutService.layoutParams.height = LayoutParams.WRAP_CONTENT
            }
        }
    }

    private fun setCheckBox() {
        binding.cbConsentAll.setOnClickListener {
            viewModel.changeCheckBox(30)
        }
        binding.cbConsent1.setOnClickListener {
            viewModel.changeCheckBox(2)
        }
        binding.cbConsent2.setOnClickListener {
            viewModel.changeCheckBox(3)
        }
        binding.cbConsent3.setOnClickListener {
            viewModel.changeCheckBox(5)
        }
    }

    private fun setObserver() {
        viewModel.checkBoxState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                checkAllCheckBox()
            }
        }
    }

    private fun checkAllCheckBox() {
        if (viewModel.checkBoxState.value == 30) {
            binding.cbConsentAll.isChecked = true
            binding.cbConsent1.isChecked = true
            binding.cbConsent2.isChecked = true
            binding.cbConsent3.isChecked = true
        } else if (viewModel.checkBoxState.value == 1) {
            binding.cbConsent1.isChecked = false
            binding.cbConsent2.isChecked = false
            binding.cbConsent3.isChecked = false
        } else if (viewModel.checkBoxState.value != 30) {
            binding.cbConsentAll.isChecked = false
        }
    }
}