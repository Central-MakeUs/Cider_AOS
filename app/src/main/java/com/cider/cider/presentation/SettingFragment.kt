package com.cider.cider.presentation

import android.os.Bundle
import android.view.View
import com.cider.cider.BuildConfig
import com.cider.cider.R
import com.cider.cider.databinding.FragmentSettingBinding
import com.cider.cider.utils.binding.BindingFragment

class SettingFragment: BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvVersionNum.text = BuildConfig.VERSION_CODE.toString()

    }

}