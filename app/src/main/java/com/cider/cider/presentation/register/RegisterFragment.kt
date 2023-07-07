package com.cider.cider.presentation.register

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterBinding
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.Gender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment
    :BindingFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private lateinit var callback: OnBackPressedCallback
    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.register = viewModel
        binding.lifecycleOwner = this@RegisterFragment

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("Kakao Login Test","사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.e("Kakao Login Test","사용자 정보 요청 성공 $user\n" +
                        "${user.kakaoAccount?.profile?.nickname}\n" +
                        "${user.kakaoAccount?.birthday}\n" +
                        "${user.kakaoAccount?.gender}\n" )
                viewModel.getRegisterData(
                    name = user.kakaoAccount?.profile?.nickname,
                    date = user.kakaoAccount?.birthday?.toInt(),
                    gender = if (user.kakaoAccount?.gender == Gender.MALE) com.cider.cider.domain.type.Gender.MALE
                            else com.cider.cider.domain.type.Gender.FEMALE
                )
            }
        }


        setFragment(RegisterConsentFragment(),"RegisterConsent")
        setButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onDestroyView() {
        parentFragmentManager.beginTransaction().remove(RegisterFragment()).commit()
        super.onDestroyView()
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_register, fragment, tag)
        transaction.addToBackStack("Register")
        transaction.commit()
    }

    private fun setButton() {
        binding.btnRegister.setOnClickListener {
            when (childFragmentManager.fragments[childFragmentManager.fragments.size-1].tag) {
                "RegisterConsent" -> {
                    setFragment(RegisterNicknameFragment(), "RegisterNickname")
                    Log.d("Fragment Test","1 ${childFragmentManager.fragments}")
                }
                "RegisterNickname" -> {
                    setFragment(RegisterProfileFragment(), "RegisterProfile")
                    Log.d("Fragment Test","2 ${childFragmentManager.fragments}")
                }
                "RegisterProfile" -> {
                    setFragment(RegisterKeywordFragment(), "RegisterKeyword")
                    Log.d("Fragment Test","3 ${childFragmentManager.fragments}")
                }
                "RegisterKeyword" -> {
                    setFragment(RegisterCompletionFragment(), "RegisterCompletion")
                    binding.viewRegisterTap.visibility = View.GONE
                    binding.tvToolbarTitle.text = "회원가입 완료"
                    //TODO(완료 시 어디로 가지?)
                    Log.d("Fragment Test","4 ${parentFragmentManager.fragments}")
                }
                "RegisterCompletion" -> {
                    Toast.makeText(requireContext(),"완료",Toast.LENGTH_SHORT).show()
                }
                null -> {
                    Log.d("Fragment Test","5 ${childFragmentManager.fragments}")
                    //TODO(예외 처리)
                }
                else -> {}
            }
        }

        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun onBackPressed() {
        when (childFragmentManager.fragments[childFragmentManager.fragments.size-1].tag) {
            "RegisterConsent" -> {
                parentFragmentManager.popBackStack()
            }
            "RegisterCompletion" -> {
                //TODO(None)
            }
            else -> {
                childFragmentManager.popBackStack()
            }
        }
    }
}