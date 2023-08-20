package com.cider.cider.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemChallengeOngoingBinding
import com.cider.cider.databinding.ItemChallengeReviewBinding
import com.cider.cider.domain.model.ChallengeOngoingModel
import com.cider.cider.domain.model.ChallengeReviewModel
import com.cider.cider.utils.ItemDiffCallback

class ChallengeReviewAdapter(): ListAdapter<ChallengeReviewModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<ChallengeReviewModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CardVieHolder(ItemChallengeReviewBinding.inflate(inflater, parent, false))
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
        private val binding: ItemChallengeReviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChallengeReviewModel) {
            binding.challenge = item

            binding.btnDelete.setOnClickListener {
                listener?.onItemClick(item.id,0)
            }

            binding.btnFail.setOnClickListener {
                listener?.onItemClick(item.id,1)
            }

            binding.btnReturn.setOnClickListener {
                listener?.onItemClick(item.id,2)
            }

            binding.btnSuccess.setOnClickListener {
                listener?.onItemClick(item.id,3)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id:Int, type: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}