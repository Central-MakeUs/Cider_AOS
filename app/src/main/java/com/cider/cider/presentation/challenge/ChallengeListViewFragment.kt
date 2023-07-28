package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeListViewBinding
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.viewmodel.ChallengeHomeViewModel
import com.cider.cider.presentation.viewmodel.ChallengeViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.binding.BindingFragmentNoNavi
import com.cider.cider.utils.decoration.ItemSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeListViewFragment(private val type: String): BindingFragmentNoNavi<FragmentChallengeListViewBinding>(R.layout.fragment_challenge_list_view) {

    private val viewModel: ChallengeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TEST ListView",type)
        when (type) {
            Challenge.INVESTING.text -> {
                viewModel.getChallengeCategory(Challenge.INVESTING)
            }
            Challenge.SAVING.text -> {
                viewModel.getChallengeCategory(Challenge.SAVING)
            }
            Challenge.MONEY_MANAGEMENT.text -> {
                viewModel.getChallengeCategory(Challenge.MONEY_MANAGEMENT)
            }
            Challenge.FINANCIAL_LEARNING.text -> {
                viewModel.getChallengeCategory(Challenge.FINANCIAL_LEARNING)
            }
            "popular" -> {
                viewModel.getChallengePopular()
            }
            "official" -> {
                viewModel.getChallengeOfficial()
            }
        }
        setChallengeView()
    }

    private fun setChallengeView() {
        //List 요청을 bundle 받아서 다르게
        val cardAdapter = ChallengeCardAdapter()

        binding.rvChallenge.apply {
            adapter = cardAdapter
            addItemDecoration(ItemSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.challenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                cardAdapter.submitList(it)
            }
        }
    }
}