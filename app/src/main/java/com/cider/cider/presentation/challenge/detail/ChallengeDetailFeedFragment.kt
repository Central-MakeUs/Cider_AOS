package com.cider.cider.presentation.challenge.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailFeedBinding
import com.cider.cider.domain.type.Filter
import com.cider.cider.presentation.adapter.CertifyAdapter
import com.cider.cider.presentation.adapter.CertifyDetailAdapter
import com.cider.cider.presentation.adapter.DetailImageListAdapter
import com.cider.cider.presentation.adapter.ImageListAdapter
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeDetailFeedFragment: BindingFragment<FragmentChallengeDetailFeedBinding>(R.layout.fragment_challenge_detail_feed) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        setFeedList()
        setFilter()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val imageListAdapter = DetailImageListAdapter()

        binding.rvImage.apply {
            adapter = imageListAdapter
            layoutManager = GridLayoutManager(requireContext(),
                3, RecyclerView.VERTICAL, false)
        }

        viewModel.imageList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                imageListAdapter.submitList(it)
            }
        }
    }

    private fun setFilter() {
        binding.btnFilter.setOnClickListener {
            if (binding.tvFilter.text == "최신순") {
                viewModel.detail.value?.let { it1 -> viewModel.getCertify(it1.challengeId, Filter.LIKE) }
                binding.tvFilter.text = "좋아요순"
            } else {
                viewModel.detail.value?.let { it1 -> viewModel.getCertify(it1.challengeId, Filter.LATEST) }
                binding.tvFilter.text = "최신순"
            }
        }
    }

    private fun setFeedList() {
        val certifyAdapter = CertifyDetailAdapter()

        binding.rvFeed.apply {
            adapter = certifyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.certify.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                certifyAdapter.submitList(it)
            }
        }
    }

    override fun onBackPressed() {
        val parentNavController = requireActivity().findNavController(R.id.fl_main)
        parentNavController.popBackStack()
    }
}