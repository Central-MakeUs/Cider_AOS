package com.cider.cider.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemChallengeCardBinding
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.presentation.viewmodel.ChallengeListViewModel
import com.cider.cider.utils.ItemDiffCallback

class ChallengeCardAdapter(private val viewModel : ChallengeListViewModel): ListAdapter<ChallengeCardModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<ChallengeCardModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChallengeCardVieHolder(ItemChallengeCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is ChallengeCardVieHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class ChallengeCardVieHolder(
        private val binding: ItemChallengeCardBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChallengeCardModel) {
            binding.challenge = item
            binding.vm = viewModel
        }
    }
}