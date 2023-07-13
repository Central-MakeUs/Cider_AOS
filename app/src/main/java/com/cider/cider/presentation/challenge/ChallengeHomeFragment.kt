package com.cider.cider.presentation.challenge

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeHomeBinding
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.adapter.ImageListAdapter
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.presentation.viewmodel.ChallengeViewModel
import com.cider.cider.utils.ItemSpacingDecoration
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeHomeFragment: BindingFragment<FragmentChallengeHomeBinding>(R.layout.fragment_challenge_home) {

    private val viewModel: ChallengeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBar()
        setButton()
        setRecyclerView()
    }

    private fun setAppBar() {
        binding.toolbar.tvToolbarTitle.text = "챌린지"
        binding.toolbar.btnToolbarBack.visibility = View.INVISIBLE
        binding.toolbar.btnToolbarIcon1.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.line_my_24))

        binding.toolbarPopularChallenge.toolbarTitle.text = "인기 챌린지"
        binding.toolbarOfficialChallenge.toolbarTitle.text = "바로 참여 가능! 공식 챌린지"
        binding.toolbarCategoryChallenge.toolbarTitle.text = "카테고리"
        binding.toolbarCategoryChallenge.tvMore.text = "전체 챌린지 보기"
    }

    private fun setRecyclerView () {
        setPopularChallenge()
        setOfficialChallenge()

        binding.scrollView.setOnRefreshListener {
            //TODO(로딩할 때 이거 쓰면 됨)
            binding.scrollView.isRefreshing = false
        }

    }

    private fun setPopularChallenge() {
        val popularCardAdapter = ChallengeCardAdapter()
        binding.rvPopularChallenge.apply {
            adapter = popularCardAdapter
            addItemDecoration(ItemSpacingDecoration(0,0,resources.getDimensionPixelSize(R.dimen.challenge_card_between),0))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.popularChallenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                popularCardAdapter.submitList(it)
            }
        }
    }

    private fun setOfficialChallenge() {
        val officialCardAdapter = ChallengeCardAdapter()
        binding.rvOfficialChallenge.apply {
            adapter = officialCardAdapter
            addItemDecoration(ItemSpacingDecoration(0,0,resources.getDimensionPixelSize(R.dimen.challenge_card_between),0))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.officialChallenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                officialCardAdapter.submitList(it)
            }
        }
    }

    private fun setButton() {
        binding.btnTest.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeCreateFragment
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        requireActivity().finish()
    }
}