package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeListViewBinding
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.viewmodel.ChallengeListViewModel
import com.cider.cider.utils.binding.BindingFragmentNoNavi
import com.cider.cider.utils.decoration.ItemSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeListViewFragment(private val type: String): BindingFragmentNoNavi<FragmentChallengeListViewBinding>(R.layout.fragment_challenge_list_view) {

    private val viewModel: ChallengeListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.executePendingBindings()
        binding.lifecycleOwner = viewLifecycleOwner

        when (type) {
            Category.INVESTING.text -> {
                viewModel.getChallengeCategory(Category.INVESTING)
            }
            Category.SAVING.text -> {
                viewModel.getChallengeCategory(Category.SAVING)
            }
            Category.MONEY_MANAGEMENT.text -> {
                viewModel.getChallengeCategory(Category.MONEY_MANAGEMENT)
            }
            Category.FINANCIAL_LEARNING.text -> {
                viewModel.getChallengeCategory(Category.FINANCIAL_LEARNING)
            }
            "popular" -> {
                viewModel.getChallengePopular(Filter.LATEST)
            }
            "official" -> {
                viewModel.getChallengeOfficial(Filter.LATEST)
            }
        }
        Log.d("TEST ListView","$type : ${viewModel.challenge.value?.size}")
        setChallengeView()
        setButton()
    }

    private fun setButton() {
        binding.btnChallengeLook.setOnClickListener {
            findNavController().navigate(R.id.action_challengeHomeFragment_to_challengeCreateFragment)
        }
    }

    private fun setChallengeView() {
        //List 요청을 bundle 받아서 다르게
        val cardAdapter = ChallengeCardAdapter(viewModel)

        cardAdapter.setOnItemClickListener(object : ChallengeCardAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                val bundle = bundleOf("id" to id)
                findNavController().navigate(R.id.action_challengeHomeFragment_to_challengeDetailFragment, bundle)
            }
        })

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