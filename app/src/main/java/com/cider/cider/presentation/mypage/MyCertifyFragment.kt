package com.cider.cider.presentation.mypage

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentMyCertifyBinding
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCertifyFragment: BindingFragment<FragmentMyCertifyBinding>(R.layout.fragment_my_certify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setButton()
        setSpinner()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "나의 인증글"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
        binding.toolbar.tvTotal.text = "총 n개"
    }

    private fun setButton() {
        binding.btnChallengeLook.setOnClickListener {
            findNavController().navigate(
                R.id.action_myCertifyFragment_to_challengeHomeFragment
            )
        }
    }

    private fun setSpinner() {
        val itemArray = arrayOf("3", "4", "5", "6", "7", "8", "랜덤")
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner_dropdown,
            itemArray
        )
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spinnerChallenge.adapter = spinnerAdapter
        binding.spinnerChallenge.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // 선택됬을 경우

                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}