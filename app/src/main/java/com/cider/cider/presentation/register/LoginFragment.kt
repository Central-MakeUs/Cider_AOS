package com.cider.cider.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.View
import com.cider.cider.R
import com.cider.cider.databinding.FragmentLoginBinding
import com.cider.cider.utils.binding.BindingFragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterKakao.setOnClickListener {

            val mCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("Kakao Login Test","로그인 실패 $error")
                } else {
                    Log.e("Kakao Login Test","로그인 성공 ${token?.accessToken}")
                }
            }

            //카카오톡 설치 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                //카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                    if (error != null) {
                        Log.e("Kakao Login Test","로그인 실패 $error")
                        //사용자가 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        } else {
                            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = mCallBack)
                        }
                        //다른 오류
                    } else if (token != null) {
                        Log.e("Kakao Login Test","로그인 성공 ${token?.accessToken}")
                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.fl_login, RegisterFragment(), "Register")
                            addToBackStack("Register")
                            commit()
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = mCallBack)
            }


        }
    }

}