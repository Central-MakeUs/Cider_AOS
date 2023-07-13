package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.cider.cider.R
import com.cider.cider.databinding.DialogBottomSheetParticiationCapacityBinding
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingBottomSheet

class CapacityBottomSheetDialog(): BindingBottomSheet<DialogBottomSheetParticiationCapacityBinding>(
    R.layout.dialog_bottom_sheet_particiation_capacity) {

    private val viewModel: ChallengeCreateViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSelect.setOnClickListener {
            viewModel
            dismiss()
        }
    }
}