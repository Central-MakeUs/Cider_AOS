package com.cider.cider.presentation.challenge.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailFeedBinding
import com.cider.cider.databinding.FragmentChallengeDetailInfoBinding
import com.cider.cider.presentation.adapter.FeedAdapter
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.presentation.viewmodel.ChallengeHomeViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeDetailFeedFragment: BindingFragment<FragmentChallengeDetailFeedBinding>(R.layout.fragment_challenge_detail_feed) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFeedList()
    }
    private fun setFeedList() {
        val feedAdapter = FeedAdapter()

        viewModel.testFeed(requireContext())

        binding.rvFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.feed.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                feedAdapter.submitList(it)
            }
        }
    }

    override fun onBackPressed() {
        val parentNavController = requireActivity().findNavController(R.id.fl_main)
        parentNavController.popBackStack()
    }
}