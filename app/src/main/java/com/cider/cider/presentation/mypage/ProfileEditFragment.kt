package com.cider.cider.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentProfileEditBinding
import com.cider.cider.utils.binding.BindingFragment

class ProfileEditFragment: BindingFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "프로필 수정"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}