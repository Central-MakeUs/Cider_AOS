package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.DialogChallengeExitBinding
import com.cider.cider.databinding.DialogParticipateBinding
import com.cider.cider.databinding.DialogReasonBinding
import com.cider.cider.databinding.ItemAlertDialogBinding
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingDialog

class ChallengeParticipateDialog: BindingDialog<DialogParticipateBinding>(R.layout.dialog_participate) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.executePendingBindings()

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCheck.setOnClickListener {
            //participate
            dismiss()
        }

        binding.icon2.setOnClickListener {
            //participate
            dismiss()
        }
    }
}