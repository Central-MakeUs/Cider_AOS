package com.cider.cider.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.DialogParticipateBinding
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingDialog
import kotlinx.coroutines.launch

class ChallengeParticipateDialog: BindingDialog<DialogParticipateBinding>(R.layout.dialog_participate) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.executePendingBindings()

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCheck.setOnClickListener {
            lifecycleScope.launch {
                if (!viewModel.participateChallenge()) {
                    Toast.makeText(requireContext(), "챌린지 참여에 실패했습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "챌린지 참여가 완료되었습니다", Toast.LENGTH_SHORT).show()
                    binding.tvDialogTitle.text = "챌린지에 참여했어요\n인증하러 가요"
                    binding.btnCheck.visibility = View.GONE
                }
                dismiss()
            }
        }

        binding.icon2.setOnClickListener {
            dismiss()
        }
    }
}