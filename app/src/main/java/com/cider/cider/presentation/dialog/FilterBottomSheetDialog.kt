package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.cider.cider.R
import com.cider.cider.databinding.DialogBottomSheetFilterBinding
import com.cider.cider.databinding.DialogBottomSheetWritingBinding
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.WriteType
import com.cider.cider.utils.binding.BindingBottomSheet

class FilterBottomSheetDialog(): BindingBottomSheet<DialogBottomSheetFilterBinding>(
    R.layout.dialog_bottom_sheet_filter) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnLatest.setOnClickListener {
            mListener?.onValueUpdated(Filter.LATEST)
            dismiss()
        }

        binding.btnLike.setOnClickListener {
            mListener?.onValueUpdated(Filter.LIKE)
            dismiss()
        }

        binding.btnParticipate.setOnClickListener {
            mListener?.onValueUpdated(Filter.PARTICIPATE)
            dismiss()
        }


    }


    interface OnValueChangedListener {
        fun onValueUpdated(type: Filter)
    }

    private var mListener: OnValueChangedListener? = null

    fun setOnValueChangedListener(listener: OnValueChangedListener) {
        mListener = listener
    }
}