package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeHomeBinding
import com.cider.cider.domain.model.BannerCardModel
import com.cider.cider.domain.type.WriteType
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.presentation.adapter.BannerPagerAdapter
import com.cider.cider.presentation.adapter.CertifyAdapter
import com.cider.cider.presentation.dialog.WriteBottomSheetDialog
import com.cider.cider.presentation.viewmodel.CertifyViewModel
import com.cider.cider.presentation.viewmodel.ChallengeHomeViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeHomeFragment: BindingFragment<FragmentChallengeHomeBinding>(R.layout.fragment_challenge_home) {

    private val viewModel: ChallengeHomeViewModel by activityViewModels()
    private val certify: CertifyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        setAppBar()
        setButton()
        setRecyclerView()
        setScrollEvent()
        setCategory()
        setBottomNavi()
        setBanner()
    }

    private fun setBanner() {
        val bannerAdapter = BannerPagerAdapter(requireContext())
        binding.bannerHome.adapter = bannerAdapter
        val bannerItems = listOf(
            BannerCardModel(1),
        )
        bannerAdapter.submitList(bannerItems)

        val current = 1
        val total = bannerAdapter.itemCount

        val indicator = "$current / $total"

        binding.tvIndicator.text = indicator

        binding.bannerHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val currentPage = position + 1
                val totalPages = bannerAdapter.itemCount

                val indicatorText = "$currentPage / $totalPages"
                binding.tvIndicator.text = indicatorText
            }
        })
    }

    private fun setAppBar() {
        binding.toolbar.tvToolbarTitle.text = "챌린지"
        binding.toolbar.btnToolbarIcon1.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.line_my_24))

        binding.toolbar.btnToolbarIcon1.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeMyFragment
            )
        }

        binding.toolbarPopularChallenge.toolbarTitle.text = "인기 챌린지"
        binding.toolbarPopularChallenge.btnMore.setOnClickListener {
            val bundle = Bundle().apply {
                putString("type","popular")
            }
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeListFragment, bundle
            )
        }

        binding.toolbarOfficialChallenge.toolbarTitle.text = "바로 참여 가능! 공식 챌린지"
        binding.toolbarOfficialChallenge.btnMore.setOnClickListener {
            val bundle = Bundle().apply {
                putString("type","official")
            }
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeListFragment, bundle
            )
        }

        binding.toolbarCategoryChallenge.toolbarTitle.text = "카테고리"
        binding.toolbarCategoryChallenge.tvMore.text = "전체 챌린지 보기"
        binding.toolbarCategoryChallenge.btnMore.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeListAllFragment
            )
        }

        binding.toolbarRecommendFeed.toolbarTitle.text = "추천 피드"
        binding.toolbarRecommendFeed.tvMore.text = "오늘의 활동 추천 피드"
        binding.toolbarRecommendFeed.ivMore.visibility = View.GONE
    }

    private fun setRecyclerView () {
        setChallengeList()
        setFeedList()
    }

    private fun setScrollEvent() {
/*        binding.scrollView.setOnRefreshListener {
            //TODO(로딩할 때 이거 쓰면 됨)
            binding.scrollView.isRefreshing = false
        }*/
    }

    private fun setChallengeList() {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fl_popular_challenge, ChallengeListViewFragment("popular"))
            replace(R.id.fl_official_challenge, ChallengeListViewFragment("official"))
            replace(R.id.fl_category_challenge, ChallengeListViewFragment(Category.INVESTING.text))
            commit()
        }
    }

    private fun setCategory() {
        viewModel.tabState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                when (it) {
                    Category.INVESTING -> {
                        setCategoryFragment(it)
                    }
                    Category.FINANCIAL_LEARNING -> {
                        setCategoryFragment(it)
                    }
                    Category.MONEY_MANAGEMENT -> {
                        setCategoryFragment(it)
                    }
                    Category.SAVING -> {
                        setCategoryFragment(it)
                    } //TODO(입력 값 다르게 호출하는 api가 각각 다름)
                }
            }
        }

    }
    private fun setCategoryFragment(challenge: Category) {
        val fragment = ChallengeListViewFragment(challenge.text)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fl_category_challenge, fragment)
            commit() //TODO(데이터 부를 때마다 초기화 되는 문제 해결)
        }
    }

    private fun setFeedList() {
        val certifyAdapter = CertifyAdapter(certify)

        binding.rvRecommendFeed.apply {
            adapter = certifyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        certify.certifyList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                certifyAdapter.submitList(it)
            }
        }
        certify.getCertify()
    }

    private fun setButton() {
        binding.btnChallengeAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeHomeFragment_to_challengeCreateFragment
            )
        }

        binding.fabTop.setOnClickListener {
            binding.scrollView.scrollY = 0
        }
    }

    private fun setBottomNavi() {
        binding.btnTest.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_challenge -> {
                    true
                }
                R.id.item_write -> {
                    showWriteBottomSheetDialog()
                    false
                }
                R.id.item_my -> {
                    //TODO("Test용")
                    findNavController().navigate(
                        R.id.action_challengeHomeFragment_to_myPageFragment
                    )
                    false
                }
                else -> {false}
            }
        }
    }

    private fun showWriteBottomSheetDialog() {
        val dialog = WriteBottomSheetDialog()

        dialog.setOnValueChangedListener(object : WriteBottomSheetDialog.OnValueChangedListener {

            override fun onValueUpdated(type: WriteType) {
                when (type) {
                    WriteType.CREATE -> {
                        findNavController().navigate(
                            R.id.action_challengeHomeFragment_to_challengeCreateFragment
                        )
                    }
                    WriteType.AUTH -> {
                        lifecycleScope.launch {
                            if (viewModel.checkChallengeParticipateList()) {
                                Toast.makeText(requireContext(),"인증 가능한 챌린지가 없습니다.",Toast.LENGTH_SHORT).show()
                            } else {
                                findNavController().navigate(
                                    R.id.action_challengeHomeFragment_to_certifyFragment
                                )
                            }
                        }
                    }
                }
            }

        })
        dialog.show(parentFragmentManager, "Capacity")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        requireActivity().finish()
    }
}