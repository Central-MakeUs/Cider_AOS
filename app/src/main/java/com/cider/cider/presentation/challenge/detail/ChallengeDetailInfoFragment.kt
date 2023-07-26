package com.cider.cider.presentation.challenge.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailInfoBinding
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingFragment

class ChallengeDetailInfoFragment: BindingFragment<FragmentChallengeDetailInfoBinding>(R.layout.fragment_challenge_detail_info) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCaution()
    }

    private fun setCaution() {
        binding.ivCaution.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                binding.tvCaution.visibility = View.VISIBLE
            } else {
                binding.tvCaution.visibility = View.GONE
            }
        }

    }

    override fun onBackPressed() {
        val parentNavController = requireActivity().findNavController(R.id.fl_main)
        parentNavController.popBackStack()
    }
}