package com.cider.cider.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemImageFeedBinding
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.utils.ItemDiffCallback

class FeedImageAdapter() : ListAdapter<ImageCardModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<ImageCardModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.uri == new.uri}
    )
){
    inner class ItemViewHolder(
        private val binding: ItemImageFeedBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageCardModel) {
            binding.image = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemImageFeedBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is ItemViewHolder -> {
                holder.bind(item)
            }
        }
    }
}