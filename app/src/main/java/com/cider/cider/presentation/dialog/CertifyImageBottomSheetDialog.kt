package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.cider.cider.R
import com.cider.cider.databinding.DialogBottomSheetCertifyImageBinding
import com.cider.cider.databinding.DialogBottomSheetProfileEditBinding
import com.cider.cider.domain.type.ProfileEdit
import com.cider.cider.utils.binding.BindingBottomSheet

class CertifyImageBottomSheetDialog(): BindingBottomSheet<DialogBottomSheetCertifyImageBinding>(
    R.layout.dialog_bottom_sheet_certify_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCamera.setOnClickListener {
            mListener?.onValueUpdated(ProfileEdit.CAMERA)
            dismiss()
        }

        binding.btnGallery.setOnClickListener {
            mListener?.onValueUpdated(ProfileEdit.GALLERY)
            dismiss()
        }
    }


    interface OnValueChangedListener {
        fun onValueUpdated(type: ProfileEdit)
    }

    private var mListener: OnValueChangedListener? = null

    fun setOnValueChangedListener(listener: OnValueChangedListener) {
        mListener = listener
    }
}