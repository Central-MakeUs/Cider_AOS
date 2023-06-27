package com.cider.cider.presentation.register

import android.os.Bundle
import android.view.View
import com.cider.cider.R
import com.cider.cider.databinding.FragmentLoginBinding
import com.cider.cider.utils.binding.BindingFragment

class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterKakao.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_login, RegisterFragment(), "Register")
                addToBackStack("Register")
                commit()
            }
        }
    }

}