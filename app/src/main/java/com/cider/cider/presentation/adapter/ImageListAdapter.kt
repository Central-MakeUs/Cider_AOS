package com.cider.cider.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemImageListBinding
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.utils.ItemDiffCallback

class ImageListAdapter (
        ): ListAdapter<ImageCardModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<ImageCardModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.uri == new.uri}
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(ItemImageListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ImageViewHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class ImageViewHolder(
        private val binding: ItemImageListBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageCardModel) {
            binding.image = item
            binding.executePendingBindings()

            Log.d("TEST image","viewholder $item")
        }
    }
}
