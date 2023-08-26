package com.cider.cider.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentMyPageBinding
import com.cider.cider.domain.type.WriteType
import com.cider.cider.presentation.dialog.WriteBottomSheetDialog
import com.cider.cider.presentation.viewmodel.MyPageViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment: BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page)  {

    private val viewModel: MyPageViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        binding.sectionCharacter.scaleX = 0.66f
        binding.sectionCharacter.scaleY = 0.66f
        setBottomSheet()
        setButton()
        setBottomNavi()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyPageData()
    }

    private fun setBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            // Handle other states if necessary
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("TEST slide","$slideOffset")
                binding.sectionCharacter.animate().scaleX(1-slideOffset * 0.34f).scaleY(1-slideOffset * 0.34f).setDuration(0).start();
            }

        })
    }


    private fun setBottomNavi() {
        binding.btnTest.selectedItemId = R.id.item_my
        binding.btnTest.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_challenge -> {
                    findNavController().navigate(
                        R.id.action_myPageFragment_to_challengeHomeFragment
                    )
                    false
                }
                R.id.item_write -> {
                    showWriteBottomSheetDialog()
                    false
                }
                R.id.item_my -> {
                    true
                }
                else -> {false}
            }
        }
    }

    private fun setButton() {
        binding.btnMyCertify.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_myCertifyFragment
            )
        }

        binding.btnInterest.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_challengeInterestFragment
            )
        }

        binding.btnChallengeCurrent.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_challengeMyFragment
            )
        }

        binding.ivProfileEdit.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_profileEditFragment
            )
        }
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(
                R.id.action_myPageFragment_to_settingFragment
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    private fun showWriteBottomSheetDialog() {
        val dialog = WriteBottomSheetDialog()

        dialog.setOnValueChangedListener(object : WriteBottomSheetDialog.OnValueChangedListener {

            override fun onValueUpdated(type: WriteType) {
                when (type) {
                    WriteType.CREATE -> {
                        findNavController().navigate(
                            R.id.action_myPageFragment_to_challengeCreateFragment
                        )
                    }
                    WriteType.AUTH -> {
                        findNavController().navigate(
                            R.id.action_myPageFragment_to_certifyFragment
                        )
                    }
                }
            }

        })
        dialog.show(parentFragmentManager, "Capacity")
    }
}