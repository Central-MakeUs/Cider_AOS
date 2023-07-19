package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.cider.cider.R
import com.cider.cider.databinding.DialogBottomSheetNumPickerBinding
import com.cider.cider.domain.type.BottomSheetType
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingBottomSheet

class NumPickerBottomSheetDialog(private val type: BottomSheetType?): BindingBottomSheet<DialogBottomSheetNumPickerBinding>(
    R.layout.dialog_bottom_sheet_num_picker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSelect.setOnClickListener {
            mListener?.onValueUpdated(binding.numPicker.value, type )
            dismiss()
        }
        binding.numPicker.apply {
            maxValue = arguments?.getInt("maxValue")?:30
            minValue = arguments?.getInt("minValue")?:3
            value = arguments?.getInt("value")?:3
        }
    }


    interface OnValueChangedListener {
        fun onValueUpdated(newValue: Int, type: BottomSheetType?)
    }

    private var mListener: OnValueChangedListener? = null

    fun setOnValueChangedListener(listener: OnValueChangedListener) {
        mListener = listener
    }
}