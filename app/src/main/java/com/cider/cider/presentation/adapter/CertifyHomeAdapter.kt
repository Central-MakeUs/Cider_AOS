package com.cider.cider.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemCertifyBinding
import com.cider.cider.databinding.ItemCertifyHomeBinding
import com.cider.cider.databinding.ItemFeedBinding
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.presentation.viewmodel.CertifyViewModel
import com.cider.cider.utils.ItemDiffCallback

class CertifyHomeAdapter(
    ): ListAdapter<CertifyModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<CertifyModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CertifyVieHolder(ItemCertifyHomeBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is CertifyVieHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class CertifyVieHolder(
        private val binding: ItemCertifyHomeBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CertifyModel) {

        }
    }
}