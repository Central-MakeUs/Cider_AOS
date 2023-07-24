package com.cider.cider.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemChallengeOngoingBinding
import com.cider.cider.domain.model.ChallengeOngoingModel
import com.cider.cider.utils.ItemDiffCallback

class ChallengeOngoingAdapter(): ListAdapter<ChallengeOngoingModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<ChallengeOngoingModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CardVieHolder(ItemChallengeOngoingBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is CardVieHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class CardVieHolder(
        private val binding: ItemChallengeOngoingBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChallengeOngoingModel) {
            binding.challenge = item
        }
    }
}