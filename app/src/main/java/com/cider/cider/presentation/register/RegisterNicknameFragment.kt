package com.cider.cider.presentation.register

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterNicknameBinding
import com.cider.cider.domain.type.EditTextState
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterNicknameFragment
    :BindingFragment<FragmentRegisterNicknameBinding>(R.layout.fragment_register_nickname) {

    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.changeRegisterState(RegisterType.INFORMATION_INPUT1)

        setNickName()
        setObserver()
    }

    private fun setNickName() {
        binding.etNickname.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_active)
            } else {
                viewModel.checkNickNameEnable()
            }
        }

        binding.etNickname.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etNickname.clearFocus()
                val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etNickname.windowToken, 0)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setObserver() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (it.isNotEmpty()) {
                    binding.btnDelete.visibility = View.VISIBLE
                } else {
                    binding.btnDelete.visibility = View.GONE
                }
            }
        }
        viewModel.nicknameState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                when (viewModel.nicknameState.value) {
                    EditTextState.NONE -> binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_none)
                    EditTextState.ACTIVE -> {
                        binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_active)
                        binding.tvCheckNickname.visibility = View.VISIBLE
                        binding.tvCheckNickname.text = "사용 가능한 닉네임입니다"
                        binding.tvCheckNickname.setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
                    }
                    EditTextState.ERROR_DUPLICATION -> {
                        binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_error)
                        binding.tvCheckNickname.visibility = View.VISIBLE
                        binding.tvCheckNickname.text = "중복된 닉네임입니다"
                        binding.tvCheckNickname.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                    }
                    EditTextState.ERROR_MIN -> {
                        binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_error)
                        binding.tvCheckNickname.visibility = View.VISIBLE
                        binding.tvCheckNickname.text = "2글자 이상이어야 합니다"
                        binding.tvCheckNickname.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                    }
                    else -> binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_none)
                }
            }
        }

    }
}