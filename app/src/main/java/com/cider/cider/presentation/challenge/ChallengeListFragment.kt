package com.cider.cider.presentation.challenge

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
        setAction()
    }

    private fun setAction() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset == 0) {
                changeToolbar(true)
            } else if (verticalOffset == -600) {
                changeToolbar(false)
            }
        })    }

    private fun setToolbar() {
        when (data) {
            "popular" -> {
                binding.toolbar.tvToolbarTitle.text = "인기 챌린지"
                binding.backgroundBanner.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.btn_mint))
                binding.tvBanner.text = "사이다에서 가장 \n인기 있는 챌린지\nTop 10"
                viewModel.getChallengePopular(filter = Filter.LATEST)
                changeToolbar(true)
            }
            "official" -> {
                binding.toolbar.tvToolbarTitle.text = "공식 챌린지"
                binding.backgroundBanner.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.btn_pink))
                binding.tvBanner.text = "운영진이 함께하는\n공식 금융 챌린지"
                viewModel.getChallengeOfficial(filter = Filter.LATEST)
                changeToolbar(true)
            }
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun changeToolbar(isExpand: Boolean) {
        if (isExpand) {
            when (data) {
                "popular" -> {
                    binding.toolbar.toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.btn_mint))
                    binding.toolbar.tvToolbarTitle.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                    binding.toolbar.btnToolbarBack.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                }
                "official" -> {
                    binding.toolbar.toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.btn_pink))
                    binding.toolbar.tvToolbarTitle.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                    binding.toolbar.btnToolbarBack.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                }
            }
        } else {
            binding.toolbar.toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.toolbar.tvToolbarTitle.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.toolbar.btnToolbarBack.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
        }
    }

    private fun setRecyclerView() {
        val cardAdapter = ChallengeCardAdapter(viewModel)
        binding.rvChallengeList.apply {
            adapter = cardAdapter
            addItemDecoration(ChallengeListSpacingDecoration(requireContext(),resources.getDimensionPixelSize(R.dimen.challenge_card_between)))
        }

        cardAdapter.setOnItemClickListener(object : ChallengeCardAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                val bundle = bundleOf("id" to id)
                findNavController().navigate(R.id.action_challengeListFragment_to_challengeDetailFragment, bundle)
            }
        })

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