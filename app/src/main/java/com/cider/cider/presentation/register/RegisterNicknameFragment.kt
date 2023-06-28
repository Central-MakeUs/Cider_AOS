package com.cider.cider.presentation.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterNicknameBinding
import com.cider.cider.domain.type.EditTextState
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
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
        binding.etNickname.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                if (!binding.etNickname.text.isNullOrEmpty()) {
                    if (binding.etNickname.text!!.isNotEmpty() || binding.etNickname.text!!.length >= 2) {
                        viewModel.checkNickNameEnable()
                    } else {
                        viewModel.checkNickNameEnable()
                    }
                }
            }
        })

        binding.etNickname.setOnFocusChangeListener { view, b ->  }
    }

    private fun setObserver() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (it.isNotEmpty()) {
                    if (it.length >= 2) {
                        binding.btnDelete.visibility = View.VISIBLE
                    } else {
                        binding.btnDelete.visibility = View.GONE
                    }
                }
            }
        }
        viewModel.nicknameEnable.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (it) {
                    binding.tvCheckNickname.text = "사용 가능한 닉네임입니다"
                } else {
                    binding.tvCheckNickname.text = "중복된 닉네임입니다"
                    binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_error)
                }
            }
        }
        viewModel.nicknameState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                when (viewModel.nicknameState.value) {
                    EditTextState.NONE -> binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_none)
                    EditTextState.ACTIVE -> binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_active)
                    EditTextState.ERROR -> binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_error)
                    else -> binding.etNickname.background = resources.getDrawable(R.drawable.shape_edittext_none)
                }
            }
        }

    }
}