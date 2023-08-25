package com.cider.cider.presentation.challenge.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeDetailBinding
import com.cider.cider.presentation.adapter.ChallengeDetailViewPagerAdapter
import com.cider.cider.presentation.dialog.ChallengeExitDialog
import com.cider.cider.presentation.dialog.ChallengeParticipateDialog
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Integer.max

@AndroidEntryPoint
class ChallengeDetailFragment: BindingFragment<FragmentChallengeDetailBinding>(R.layout.fragment_challenge_detail) {

    private val viewModel: ChallengeDetailViewModel by activityViewModels()
    init {
        Log.d("TEST Lifecycle","ChallengeDetailFragment")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        val id = arguments?.getInt("id")
        if (id != null) {
            Log.d("TEST Detail","$id")
            lifecycleScope.launch {
                viewModel.getDetail(id)
                setBanner()
            }
        }
        setBehavior()
        setBottomSheet()
        setBottomNavi()
        setButton()
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.clear()
    }

    private fun setBanner() {
        binding.appbar.setBackgroundColor(ContextCompat.getColor(requireContext(),viewModel.detail.value?.category?.colorResId?:R.color.btn_blue))
        binding.background.setBackgroundColor(ContextCompat.getColor(requireContext(),viewModel.detail.value?.category?.colorResId?:R.color.btn_blue))
    }

    private fun setButton() {
        binding.btnBottom.setOnClickListener {
            if (binding.tvChallengeBtn.text == "이 챌린지 참여하기") {
                showDialog()
            }
            if (binding.tvChallengeBtn.text == "오늘 참여 인증하기") {
                val bundle = Bundle().apply {
                    putInt("id",id)
                }

                findNavController().navigate(
                    R.id.action_challengeDetailFragment_to_certifyFragment, bundle
                )
            }
        }
        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showDialog() {
        val dialog = ChallengeParticipateDialog()
        dialog.show(parentFragmentManager,null)
    }

    private fun setBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.appbar.setBackgroundColor(ContextCompat.getColor(requireContext(),viewModel.detail.value?.category?.colorResId?:R.color.btn_blue))
                        binding.btnToolbarBack.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                        binding.btnToolbarIcon.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                        binding.background.setBackgroundColor(ContextCompat.getColor(requireContext(),viewModel.detail.value?.category?.colorResId?:R.color.btn_blue))
                        binding.viewProfile.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.appbar.setBackgroundColor(Color.WHITE)
                        binding.btnToolbarBack.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
                        binding.btnToolbarIcon.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
                        binding.background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                        binding.viewProfile.visibility = View.GONE
                    }
                    // Handle other states if necessary
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.viewProfile.animate().scaleX(1-slideOffset).scaleY(1-slideOffset).setDuration(0).start();
            }

        })
    }

    private fun setBottomSheet() {
        val pagerAdapter = ChallengeDetailViewPagerAdapter(requireActivity())

        pagerAdapter.addFragment(ChallengeDetailInfoFragment())
        pagerAdapter.addFragment(ChallengeDetailFeedFragment())
        binding.vpChallenge.adapter = pagerAdapter

        val tabTitle = listOf<String>("챌린지 정보","피드")
        TabLayoutMediator(binding.tabLayout, binding.vpChallenge) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        binding.vpChallenge.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback () {
            override fun onPageSelected(position: Int) {
                var maxHeight = 0

                val fragment = pagerAdapter.fragments[position]
                val fragmentView = fragment.view

                fragmentView?.let {
                    it.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                    val fragmentHeight = it.measuredHeight
                    maxHeight = max(maxHeight, fragmentHeight)
                }


                val layoutParams = binding.vpChallenge.layoutParams
                layoutParams.height = maxHeight
                binding.vpChallenge.layoutParams = layoutParams
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setBottomNavi() {
        binding.btnLike.setOnClickListener {
            viewModel.changeLike2(binding.ivLike.isSelected)
            if (binding.ivLike.isSelected) {
                binding.ivLike.isSelected = false
                binding.tvLike.text = (binding.tvLike.text.toString().toInt() - 1).toString()
            } else {
                binding.ivLike.isSelected = true
                binding.tvLike.text = (binding.tvLike.text.toString().toInt() + 1).toString()
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}