package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentMyChallengeBinding
import com.cider.cider.presentation.adapter.ChallengeCardFinishAdapter
import com.cider.cider.presentation.adapter.ChallengeOngoingAdapter
import com.cider.cider.presentation.adapter.ChallengeReviewAdapter
import com.cider.cider.presentation.viewmodel.MyChallengeViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.decoration.ChallengeListSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeMyFragment: BindingFragment<FragmentMyChallengeBinding>(R.layout.fragment_my_challenge) {

    private val viewModel: MyChallengeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setToolbar()
        setButton()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "내 챌린지"
        binding.toolbar.btnToolbarIcon1.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.line_my_24))
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        val finishAdapter = ChallengeCardFinishAdapter()
        binding.rvFinishChallenge.apply {
            adapter = finishAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.challengeFinish.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                finishAdapter.submitList(it)
            }
        }

        val ongoingAdapter = ChallengeOngoingAdapter()
        binding.rvOngoingChallenge.apply {
            adapter = ongoingAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.challengeOngoing.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                ongoingAdapter.submitList(it)

            }
        }

        val reviewAdapter = ChallengeReviewAdapter()
        binding.rvReviewChallenge.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.challengeReview.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                reviewAdapter.submitList(it)
            }
        }
    }

    private fun setButton() {
        binding.btnChallengeAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeMyFragment_to_challengeCreateFragment
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}