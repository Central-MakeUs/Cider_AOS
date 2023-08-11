package com.cider.cider.presentation.challenge.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailInfoBinding
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailInfoFragment: BindingFragment<FragmentChallengeDetailInfoBinding>(R.layout.fragment_challenge_detail_info) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.executePendingBindings()
        binding.lifecycleOwner = viewLifecycleOwner

        setCaution()
        setBalloon()
        setProgressView()
    }

    private fun setProgressView() {
    }

    private fun setBalloon() {
        val balloon = Balloon.Builder(requireContext())
            .setArrowSize(10)
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray_7))
            .setArrowOrientation(ArrowOrientation.BOTTOM)
            .setArrowPosition(0.5f)
            .setWidthRatio(0.55f)
            .setHeight(80)
            .setTextSize(12f)
            .setCornerRadius(4f)
            .setAlpha(1f)
            .setText("함께 참여하는 유저들의\n" +
                    "이 챌린지 평균 진행 현황이에요")
            .setBalloonAnimation(BalloonAnimation.FADE)
            .build();
        binding.ivCurrentGuide.setOnClickListener {
            balloon.showAlignBottom(binding.ivCurrentGuide);
        }

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