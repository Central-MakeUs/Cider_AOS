package com.cider.cider.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
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

        binding.tvVersionNum.text = BuildConfig.VERSION_NAME
        binding.tvKakaoEmail.text = App.prefs.getString("email","")

        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnAccessTerm1.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://spiced-pentagon-516.notion.site/f54a1839ae5f43b6a839eda20124675d?pvs=4")
            startActivity(i)
        }

        binding.btnAccessTerm2.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://spiced-pentagon-516.notion.site/ccea6cb2cb6845d597cf76118fb6615c?pvs=4")
            startActivity(i)
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
                    //TODO(강제 로그아웃 처리)

                }
            }
        }

        binding.tvWithdraw.setOnClickListener {
            showPermissionDeniedDialog()
        }
    }

    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("정말 탈퇴하시겠습니까?")
        builder.setMessage("회원 탈퇴시 챌린지 내역과 내가 쓴 글이 영구적으로 삭제되며, 7일간 재가입이 불가해요.")
        builder.setPositiveButton("탈퇴하기") { _, _ ->
            lifecycleScope.launch {
                if (viewModel.signOut()) {
                    Log.d("TEST 탈퇴","성공")
                    App.prefs.setString("accessToken","")
                    App.prefs.setString("refreshToken","")
                    App.prefs.setString("email","")
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Log.d("TEST 탈퇴","실패")
                }
            }
        }
        builder.setNegativeButton("돌아가기") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

}