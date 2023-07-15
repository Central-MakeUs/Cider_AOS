package com.cider.cider.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemChallengeCardBinding
import com.cider.cider.databinding.ItemFeedBinding
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.presentation.viewmodel.ChallengeViewModel
import com.cider.cider.utils.ItemDiffCallback

class FeedAdapter(
    private val viewModel: ChallengeViewModel
    ): ListAdapter<FeedModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<FeedModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FeedVieHolder(ItemFeedBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is FeedVieHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class FeedVieHolder(
        private val binding: ItemFeedBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedModel) {
            binding.feed = item
            binding.viewmodel = viewModel
            binding.executePendingBindings()

            binding.tvMoreText.setOnClickListener {
                if (binding.tvMoreText.text == "자세히 보기") {
                    binding.tvMoreText.text = "접기"
                    binding.tvFeedContent.isSingleLine = false
                } else {
                    binding.tvMoreText.text = "자세히 보기"
                    binding.tvFeedContent.isSingleLine = true
                }
            }

        }
    }
}