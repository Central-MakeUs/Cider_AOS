package com.cider.cider.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterBinding
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment
    :BindingFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.register = viewModel
        binding.lifecycleOwner = this@RegisterFragment

        setFragment(RegisterConsentFragment())
        setButton()
    }

    fun setFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_register, fragment)
        transaction.addToBackStack("Register")
        transaction.commit()
    }

    fun setButton() {
        binding.btnRegister.setOnClickListener {
            setFragment(RegisterNicknameFragment())
            Log.d("Fragment Test","${childFragmentManager.fragments}")
        }

        binding.toolbar.setOnClickListener {
            if (childFragmentManager.fragments.size != 1)
                childFragmentManager.popBackStack()
            else
                parentFragmentManager.popBackStack()
        }
    }
}