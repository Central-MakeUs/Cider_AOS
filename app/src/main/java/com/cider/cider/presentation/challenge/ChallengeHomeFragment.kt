package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeHomeBinding
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.adapter.ChallengeCardCategoryAdapter
import com.cider.cider.presentation.adapter.FeedAdapter
import com.cider.cider.presentation.viewmodel.ChallengeViewModel
import com.cider.cider.utils.decoration.ItemSpacingDecoration
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
        setScrollEvent()
    }

    private fun setAppBar() {
        binding.toolbar.tvToolbarTitle.text = "챌린지"
        binding.toolbar.btnToolbarBack.visibility = View.INVISIBLE
        binding.toolbar.btnToolbarIcon1.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.line_my_24))

        binding.toolbarPopularChallenge.toolbarTitle.text = "인기 챌린지"
        binding.toolbarPopularChallenge.btnMore.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeListFragment
            )
        }

        binding.toolbarOfficialChallenge.toolbarTitle.text = "바로 참여 가능! 공식 챌린지"
        binding.toolbarOfficialChallenge.btnMore.setOnClickListener {
            //TODO(fragment 변경)
        }

        binding.toolbarCategoryChallenge.toolbarTitle.text = "카테고리"
        binding.toolbarCategoryChallenge.tvMore.text = "전체 챌린지 보기"
        binding.toolbarCategoryChallenge.btnMore.setOnClickListener {
            //TODO(fragment 변경)
        }

        binding.toolbarRecommendFeed.toolbarTitle.text = "추천 피드"
        binding.toolbarRecommendFeed.tvMore.text = "오늘의 활동 추천 피드"
        binding.toolbarRecommendFeed.ivMore.visibility = View.GONE
    }

    private fun setRecyclerView () {
        setPopularChallenge()
        setOfficialChallenge()
        setCategoryChallenge()
        setFeedList()
    }

    private fun setScrollEvent() {
/*        binding.scrollView.setOnRefreshListener {
            //TODO(로딩할 때 이거 쓰면 됨)
            binding.scrollView.isRefreshing = false
        }*/
    }


    private fun setPopularChallenge() {
        val popularCardAdapter = ChallengeCardAdapter()
        binding.rvPopularChallenge.apply {
            adapter = popularCardAdapter
            addItemDecoration(ItemSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
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
            addItemDecoration(ItemSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.officialChallenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                officialCardAdapter.submitList(it)
            }
        }
    }
    private fun setCategoryChallenge() {
        val categoryCardAdapter = ChallengeCardCategoryAdapter()
        binding.rvCategoryChallenge.apply {
            adapter = categoryCardAdapter
            addItemDecoration(ItemSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.categoryChallenge.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                categoryCardAdapter.submitList(it)
            }
        }
    }

    private fun setFeedList() {
        val feedAdapter = FeedAdapter(viewModel)

        viewModel.testFeed(requireContext())

        binding.rvRecommendFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.feed.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                feedAdapter.submitList(it)
            }
        }
    }

    private fun setButton() {
        binding.btnChallengeAdd.setOnClickListener {
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