package com.cider.cider.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.App
import com.cider.cider.BuildConfig
import com.cider.cider.R
import com.cider.cider.databinding.FragmentSettingBinding
import com.cider.cider.presentation.register.LoginActivity
import com.cider.cider.presentation.viewmodel.LoginViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment: BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvVersionNum.text = BuildConfig.VERSION_CODE.toString()
        binding.tvKakaoEmail.text = App.prefs.getString("email","")

        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                Log.d("TEST 로그아웃","성공?")
                if (viewModel.logout()) {
                    Log.d("TEST 로그아웃","성공")
                    App.prefs.setString("accessToken","")
                    App.prefs.setString("refreshToken","")
                    App.prefs.setString("email","")
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Log.d("TEST 로그아웃","실패")
                }
            }
        }
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

}