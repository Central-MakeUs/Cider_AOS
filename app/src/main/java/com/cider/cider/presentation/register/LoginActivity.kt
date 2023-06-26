package com.cider.cider.presentation.register

import android.os.Bundle
import com.cider.cider.R
import com.cider.cider.databinding.ActivityLoginBinding
import com.cider.cider.utils.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transaction = supportFragmentManager
        transaction.beginTransaction().apply {
            add(R.id.fl_login, RegisterFragment(), "Register")
            commit()
        }
    }
}