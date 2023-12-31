package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentMyChallengeBinding
import com.cider.cider.domain.type.BottomSheetType
import com.cider.cider.presentation.adapter.ChallengeCardFinishAdapter
import com.cider.cider.presentation.adapter.ChallengeOngoingAdapter
import com.cider.cider.presentation.adapter.ChallengeReviewAdapter
import com.cider.cider.presentation.dialog.ChallengeReasonDialog
import com.cider.cider.presentation.dialog.NumPickerBottomSheetDialog
import com.cider.cider.presentation.viewmodel.MyChallengeViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.decoration.ChallengeListSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeMyFragment: BindingFragment<FragmentMyChallengeBinding>(R.layout.fragment_my_challenge) {

    private val viewModel: MyChallengeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.challenge = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setRecyclerView()
        setToolbar()
        setButton()
        setNoneChallenge()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "내 챌린지"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        val finishAdapter = ChallengeCardFinishAdapter()
        binding.rvFinishChallenge.apply {
            adapter = finishAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.challengeFinish.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                finishAdapter.submitList(it)
            }
        }

        val ongoingAdapter = ChallengeOngoingAdapter()
        binding.rvOngoingChallenge.apply {
            adapter = ongoingAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.challengeOngoing.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                ongoingAdapter.submitList(it)
            }
        }

        val reviewAdapter = ChallengeReviewAdapter()
        binding.rvReviewChallenge.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        reviewAdapter.setOnItemClickListener(object : ChallengeReviewAdapter.OnItemClickListener {
            override fun onItemClick(id: Int, type: Int) {
                Log.d("TEST item Click", "$type")

                when (type) {
                    0 -> { //삭제
                        lifecycleScope.launch(Dispatchers.Main) {
                            viewModel.deleteReviewChallenge(id)
                        }
                    }
                    1 -> { //실패
                        lifecycleScope.launch(Dispatchers.Main) {
                            //viewModel.ReviewChallenge(id)
                            showDialog(string = "실패 사유")
                        }
                    }
                    2 -> { //반려
                        lifecycleScope.launch(Dispatchers.Main) {
                            //viewModel.ReviewChallenge(id)
                            showDialog(string = "반려 사유")
                        }
                    }
                    3 -> { //성공
                        val bundle = Bundle()
                        bundle.putInt("id",id)
                        bundle.putString("type","success")
                        lifecycleScope.launch(Dispatchers.Main) {
                            //viewModel.ReviewChallenge(id)
                            findNavController().navigate(
                                R.id.action_challengeMyFragment_to_challengeDetailFragment, bundle
                            )
                        }
                    }
                }
            }
        })

        viewModel.challengeReview.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                reviewAdapter.submitList(it)
            }
        }
    }

    private fun setButton() {
        binding.btnChallengeAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeMyFragment_to_challengeCreateFragment
            )
        }
    }

    private fun setNoneChallenge() {
        binding.itemNone1.tvNone1.text = "진행중인 챌린지가 없습니다"
        binding.itemNone2.tvNone1.text = "최근 종료된 챌린지가 없습니다"
        binding.itemNone3.tvNone1.text = "심사중인 챌린지가 없습니다"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun showDialog(string: String) {
        val dialog = ChallengeReasonDialog()
        val bundle = Bundle()
        bundle.putString("type",string)
        bundle.putString("content","?")
        dialog.arguments = bundle

        dialog.show(parentFragmentManager,"")
    }
}