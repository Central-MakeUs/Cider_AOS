package com.cider.cider.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentProfileEditBinding
import com.cider.cider.presentation.viewmodel.MyPageViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment: BindingFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel: MyPageViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setBtn()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "프로필 수정"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setBtn() {
        binding.btnNameEdit.setOnClickListener {
            binding.etName.requestFocus()
        }
        binding.btnRegister.setOnClickListener {
            Log.d("TEST profile","${viewModel.profileName.value == viewModel.myPageModel.value?.name}")
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}