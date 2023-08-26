package com.cider.cider.presentation.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterConsentBinding
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.LoginViewModel
import com.cider.cider.utils.binding.BindingFragmentNoNavi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterConsentFragment
    :BindingFragmentNoNavi<FragmentRegisterConsentBinding>(R.layout.fragment_register_consent) {

    private val viewModel: LoginViewModel by activityViewModels()

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
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://spiced-pentagon-516.notion.site/f54a1839ae5f43b6a839eda20124675d?pvs=4")
            startActivity(i)
        }

        binding.btnConsent3.setOnClickListener {
            viewModel.setTermDetail(2)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://spiced-pentagon-516.notion.site/ccea6cb2cb6845d597cf76118fb6615c?pvs=4")
            startActivity(i)
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
        viewModel.detailState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
/*                if (viewModel.detailState.value == 1) {
                    binding.sv1.visibility = View.VISIBLE
                    binding.sv2.visibility = View.GONE

                    binding.btnConsent2.text = "접기"
                    binding.btnConsent3.text = "자세히 보기"
                    binding.layoutService.layoutParams.height = resources.getDimensionPixelSize(R.dimen.register_service_0dp)

                    binding.tvConsentTitle.visibility = View.GONE
                } else if (viewModel.detailState.value == 2) {
                    binding.sv2.visibility = View.VISIBLE
                    binding.sv1.visibility = View.GONE

                    binding.btnConsent3.text = "접기"
                    binding.btnConsent2.text = "자세히 보기"
                    binding.layoutService.layoutParams.height = resources.getDimensionPixelSize(R.dimen.register_service_0dp)

                    binding.tvConsentTitle.visibility = View.GONE
                } else {
                    binding.sv2.visibility = View.GONE
                    binding.btnConsent2.text = "자세히 보기"
                    binding.btnConsent3.text = "자세히 보기"
                    binding.layoutService.layoutParams.height = LayoutParams.WRAP_CONTENT

                    binding.tvConsentTitle.visibility = View.VISIBLE
                }*/
            }
        }
    }

    private fun checkAllCheckBox() {
        if (viewModel.checkBoxState.value == 30) {
            binding.cbConsentAll.isChecked = true
            binding.cbConsent1.isChecked = true
            binding.cbConsent2.isChecked = true
            binding.cbConsent3.isChecked = true
            binding.viewConsentAll.isSelected = true
        } else if (viewModel.checkBoxState.value == 1) {
            binding.cbConsent1.isChecked = false
            binding.cbConsent2.isChecked = false
            binding.cbConsent3.isChecked = false
            binding.viewConsentAll.isSelected = false
        } else if (viewModel.checkBoxState.value != 30) {
            binding.cbConsentAll.isChecked = false
            binding.viewConsentAll.isSelected = false
        }
    }
}