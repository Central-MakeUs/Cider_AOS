package com.cider.cider.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.App
import com.cider.cider.R
import com.cider.cider.databinding.FragmentLoginBinding
import com.cider.cider.presentation.MainActivity
import com.cider.cider.presentation.viewmodel.LoginViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.loginWithKakao
import com.cider.cider.utils.loginWithKakaoAccount
import com.cider.cider.utils.loginWithKakaoTalk
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.Gender
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterKakao.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    // 서비스 코드에서는 간단하게 로그인 요청하고 oAuthToken 을 받아올 수 있다.
                    val oAuthToken = UserApiClient.loginWithKakao(requireContext())


                    if (viewModel.loginFirst(oAuthToken.accessToken)) { //새로운 멤버면 false
                        viewModel.setToken()

                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e("Kakao Login Test","사용자 정보 요청 실패 $error")
                            } else if (user != null) {
                                Log.e("Kakao Login Test","사용자 정보 요청 성공 $user\n" )
                                App.prefs.setString("email", user.kakaoAccount?.email?:"")
                            }
                        }

                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.fl_login, RegisterFragment(), "Register")
                            addToBackStack("Register")
                            commit()
                        }
                    }
                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.d("KaKao Test", "사용자가 명시적으로 취소")
                    } else {
                        Log.e("KaKao Test", "인증 에러 발생", error)
                    }
                }
            }
        }
    }

}