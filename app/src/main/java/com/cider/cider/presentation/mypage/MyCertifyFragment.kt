package com.cider.cider.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cider.cider.R
import com.cider.cider.databinding.FragmentMyCertifyBinding
import com.cider.cider.presentation.adapter.CertifyAdapter
import com.cider.cider.presentation.adapter.CertifyCheckAdapter
import com.cider.cider.presentation.viewmodel.CertifyViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyCertifyFragment: BindingFragment<FragmentMyCertifyBinding>(R.layout.fragment_my_certify) {

    private val certify: CertifyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = certify
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        setToolbar()
        setButton()
        setSpinner()
        setRecyclerView()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "나의 인증글"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

        certify.certifyList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                if (it != null) {
                    binding.toolbar.tvTotal.text = "총 ${it.size}개"
                } else {
                    binding.toolbar.tvTotal.text = "총 0개"
                }
            }
        }
    }

    private fun setButton() {
        binding.btnChallengeLook.setOnClickListener {
            findNavController().navigate(
                R.id.action_myCertifyFragment_to_challengeHomeFragment
            )
        }

        binding.fabTop.setOnClickListener {
            binding.scrollView.scrollY = 0
        }
    }

    private fun setRecyclerView() {
        val certifyAdapter = CertifyCheckAdapter(certify)

        binding.rvFeed.apply {
            adapter = certifyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        certify.certifyList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                certifyAdapter.submitList(it)
            }
        }
    }

    private fun setSpinner() {
        lifecycleScope.launch {
            val itemArray = certify.getChallengeList()
            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                R.layout.item_spinner_dropdown,
                itemArray?.map {it.challengeName} ?: mutableListOf()
            )

            spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
            binding.spinnerChallenge.adapter = spinnerAdapter
            binding.spinnerChallenge.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        itemArray?.get(position)?.let { certify.getCertify(it.id) }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}