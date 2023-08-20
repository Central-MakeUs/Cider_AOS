package com.cider.cider.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentInterestChallengeBinding
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.viewmodel.ChallengeListViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.decoration.ChallengeListSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeInterestFragment: BindingFragment<FragmentInterestChallengeBinding>(R.layout.fragment_interest_challenge) {

    private val viewModel: ChallengeListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.executePendingBindings()
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setButton()
        setRecyclerView()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "관심 챌린지"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
        viewModel.challenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                if (it != null) {
                    binding.toolbar.tvTotal.text = "총 ${it.size}개"
                } else {
                    binding.toolbar.tvTotal.text = "총 0개"
                }
            }
        }
    }

    private fun setButton() {
        binding.btnChallengeLook.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeInterestFragment_to_challengeCreateFragment
            )
        }
    }

    private fun setRecyclerView() {
        viewModel.getChallengeLike()
        val cardAdapter = ChallengeCardAdapter(viewModel)
        binding.rvChallengeList.apply {
            adapter = cardAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
        }

        cardAdapter.setOnItemClickListener(object : ChallengeCardAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                val bundle = bundleOf("id" to id)
                findNavController().navigate(R.id.action_challengeInterestFragment_to_challengeDetailFragment, bundle)
            }
        })

        viewModel.challenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                cardAdapter.submitList(it)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}