package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeListBinding
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.viewmodel.ChallengeViewModel
import com.cider.cider.utils.decoration.ItemSpacingDecoration
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.decoration.ChallengeListSpacingDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeListFragment: BindingFragment<FragmentChallengeListBinding>(R.layout.fragment_challenge_list) {

    private val viewModel: ChallengeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val popularCardAdapter = ChallengeCardAdapter()
        binding.rvChallengeList.apply {
            adapter = popularCardAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
        }

        viewModel.popularChallenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                popularCardAdapter.submitList(it)
            }
        }
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }
}