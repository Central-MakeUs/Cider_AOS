package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeListBinding
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.viewmodel.ChallengeListViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.decoration.ChallengeListSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeListFragment: BindingFragment<FragmentChallengeListBinding>(R.layout.fragment_challenge_list) {

    private val viewModel: ChallengeListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val data = bundle?.getString("type")
        setToolbar(data)
        setRecyclerView()

        setButton()

    }

    private fun setToolbar(data: String?) {
        when (data) {
            "popular" -> {
                binding.toolbar.tvToolbarTitle.text = "인기 챌린지"

            }
            "official" -> {
                binding.toolbar.tvToolbarTitle.text = "공식 챌린지"
            }
            else -> {
                binding.toolbar.tvToolbarTitle.text = "전체 챌린지"
                binding.vpBanner.visibility = View.GONE
            }
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        val cardAdapter = ChallengeCardAdapter()
        binding.rvChallengeList.apply {
            adapter = cardAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
        }

        viewModel.challenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                cardAdapter.submitList(it)
            }
        }
    }

    private fun setButton() {
        binding.btnChallengeAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeListFragment_to_challengeCreateFragment
            )
        }
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }
}