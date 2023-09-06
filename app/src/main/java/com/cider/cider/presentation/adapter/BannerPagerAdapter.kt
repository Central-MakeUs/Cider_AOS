package com.cider.cider.presentation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemBannerBinding
import com.cider.cider.databinding.ItemImageFeedBinding
import com.cider.cider.domain.model.BannerCardModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.utils.ItemDiffCallback

class BannerPagerAdapter(val context: Context) : ListAdapter<BannerCardModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<BannerCardModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
){
    inner class ItemViewHolder(
        private val binding: ItemBannerBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BannerCardModel) {
            binding.tvDirect.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("https://jigeum.oopy.io/ ")
                startActivity(context, i, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemBannerBinding.inflate(inflater, parent, false))
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