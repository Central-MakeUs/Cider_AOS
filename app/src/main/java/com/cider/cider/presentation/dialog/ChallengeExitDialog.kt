package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.DialogChallengeExitBinding
import com.cider.cider.databinding.ItemAlertDialogBinding
import com.cider.cider.utils.binding.BindingDialog

class ChallengeExitDialog: BindingDialog<DialogChallengeExitBinding>(R.layout.dialog_challenge_exit) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnPositive.setOnClickListener {
            dismiss()
        }

        binding.btnNegative.setOnClickListener {
            val parentNavController = requireActivity().findNavController(R.id.fl_main)
            parentNavController.popBackStack()
            dismiss()
        }
    }
}