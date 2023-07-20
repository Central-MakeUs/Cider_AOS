package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.cider.cider.R
import com.cider.cider.databinding.DialogBottomSheetNumPickerBinding
import com.cider.cider.databinding.DialogBottomSheetWritingBinding
import com.cider.cider.domain.type.BottomSheetType
import com.cider.cider.domain.type.WriteType
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingBottomSheet

class WriteBottomSheetDialog(): BindingBottomSheet<DialogBottomSheetWritingBinding>(
    R.layout.dialog_bottom_sheet_writing) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnChallengeCreate.setOnClickListener {
            mListener?.onValueUpdated(WriteType.CREATE)
            dismiss()
        }

        binding.btnChallengeAuthentication.setOnClickListener {
            mListener?.onValueUpdated(WriteType.AUTH)
            dismiss()
        }


    }


    interface OnValueChangedListener {
        fun onValueUpdated(type: WriteType)
    }

    private var mListener: OnValueChangedListener? = null

    fun setOnValueChangedListener(listener: OnValueChangedListener) {
        mListener = listener
    }
}