package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeListBinding
import com.cider.cider.domain.type.Filter
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.dialog.FilterBottomSheetDialog
import com.cider.cider.presentation.viewmodel.ChallengeListViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.decoration.ChallengeListSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeListFragment: BindingFragment<FragmentChallengeListBinding>(R.layout.fragment_challenge_list) {

    private val viewModel: ChallengeListViewModel by viewModels()

    private var data: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        data = bundle?.getString("type")
        setToolbar()
        setRecyclerView()

        setButton()

    }

    private fun setToolbar() {
        when (data) {
            "popular" -> {
                binding.toolbar.tvToolbarTitle.text = "인기 챌린지"
                viewModel.getChallengePopular(filter = Filter.LATEST)
            }
            "official" -> {
                binding.toolbar.tvToolbarTitle.text = "공식 챌린지"
                viewModel.getChallengeOfficial(filter = Filter.LATEST)
            }
            else -> {
                binding.toolbar.tvToolbarTitle.text = "전체 챌린지"
                binding.vpBanner.visibility = View.GONE
                viewModel.getChallenge(filter = Filter.LATEST)
            }
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        val cardAdapter = ChallengeCardAdapter(viewModel)
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

        binding.btnFilter.setOnClickListener {
            showFilterBottomSheetDialog()
        }
    }

    private fun setFilter(filter: Filter){
        when (data) {
            "popular" -> {
                viewModel.getChallengePopular(filter = filter)
            }
            "official" -> {
                viewModel.getChallengeOfficial(filter = filter)
            }
            else -> {
                viewModel.getChallenge(filter = filter)
            }
        }

        when (filter) {
            Filter.LATEST -> binding.tvFilter.text = "최신순"
            Filter.LIKE -> binding.tvFilter.text = "인기순"
            Filter.PARTICIPATE -> binding.tvFilter.text = "참여순"
        }
    }

    private fun showFilterBottomSheetDialog() {
        val dialog = FilterBottomSheetDialog()

        dialog.setOnValueChangedListener(object : FilterBottomSheetDialog.OnValueChangedListener {
            override fun onValueUpdated(type: Filter) {
                setFilter(type)
            }
        })
        dialog.show(parentFragmentManager, "Capacity")
    }


    override fun onBackPressed() {
        findNavController().popBackStack()
    }
}