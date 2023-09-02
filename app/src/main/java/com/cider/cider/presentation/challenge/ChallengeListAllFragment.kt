package com.cider.cider.presentation.challenge

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeListAllBinding
import com.cider.cider.databinding.FragmentChallengeListBinding
import com.cider.cider.domain.type.Filter
import com.cider.cider.presentation.adapter.ChallengeCard2Adapter
import com.cider.cider.presentation.adapter.ChallengeCardAdapter
import com.cider.cider.presentation.dialog.FilterBottomSheetDialog
import com.cider.cider.presentation.viewmodel.ChallengeListViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.decoration.ChallengeListSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeListAllFragment: BindingFragment<FragmentChallengeListAllBinding>(R.layout.fragment_challenge_list_all) {

    private val viewModel: ChallengeListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setRecyclerView()
        setButton()

    }

    private fun setToolbar() {
        binding.tvToolbarTitle.text = "전체 챌린지"
        viewModel.getChallenge(filter = Filter.LATEST)

        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        val cardAdapter = ChallengeCard2Adapter(viewModel)
        binding.rvChallengeList.apply {
            adapter = cardAdapter
        }

        cardAdapter.setOnItemClickListener(object : ChallengeCard2Adapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                val bundle = bundleOf("id" to id)
                findNavController().navigate(R.id.action_challengeListAllFragment_to_challengeDetailFragment, bundle)
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
        viewModel.getChallenge(filter = filter)
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