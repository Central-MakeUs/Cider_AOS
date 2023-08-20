package com.cider.cider.presentation.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.cider.cider.R
import com.cider.cider.databinding.ActivityLoginBinding
import com.cider.cider.presentation.MainActivity
import com.cider.cider.presentation.viewmodel.LoginViewModel
import com.cider.cider.utils.binding.BindingActivity
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var imm: InputMethodManager

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        lifecycleScope.launch {
            if (viewModel.login()) {
                moveToMain()
            } else {
                val transaction = supportFragmentManager
                transaction.beginTransaction().apply {
                    add(R.id.fl_login, LoginFragment(), "Login")
                    commit()
                }

            }
        }
        //Log.d("Kakao Test", "keyhash : ${Utility.getKeyHash(this)}")
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if(currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}