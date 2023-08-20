package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.DialogChallengeExitBinding
import com.cider.cider.databinding.DialogReasonBinding
import com.cider.cider.databinding.ItemAlertDialogBinding
import com.cider.cider.utils.binding.BindingDialog

class ChallengeReasonDialog: BindingDialog<DialogReasonBinding>(R.layout.dialog_reason) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvDialogTitle.text = arguments?.getString("type")
        binding.tvDialogContent.text = arguments?.getString("content")

        binding.btnCheck.setOnClickListener {
            dismiss()
        }
    }
}