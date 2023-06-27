package com.cider.cider.presentation.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.activityViewModels
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterConsentBinding
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment

class RegisterConsentFragment
    :BindingFragment<FragmentRegisterConsentBinding>(R.layout.fragment_register_consent) {

    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register = viewModel
        viewModel.changeRegisterState(RegisterType.SERVICE_AGREEMENT)

        setButton()
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
}