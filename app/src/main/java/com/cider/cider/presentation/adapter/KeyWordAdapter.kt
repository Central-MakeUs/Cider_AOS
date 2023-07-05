package com.cider.cider.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemKeywordBinding
import com.cider.cider.domain.type.KeyWord
import com.cider.cider.presentation.viewmodel.RegisterViewModel
import com.cider.cider.utils.ItemDiffCallback

class KeyWordAdapter(
    private val viewModel: RegisterViewModel
): ListAdapter<KeyWord, RecyclerView.ViewHolder>(
    ItemDiffCallback<KeyWord>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.title == new.title}
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return KeyWordViewHolder(ItemKeywordBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is KeyWordViewHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class KeyWordViewHolder(
        private val binding: ItemKeywordBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KeyWord) {
            binding.keyword = item

            binding.layoutKeyword.setOnClickListener {
                Log.d("KeyWordTest","TEST $item")
                viewModel.changeKeyWordState(item.title)
            }
            binding.layoutKeyword.isSelected = item.state
            binding.itemTag.isSelected = item.state
            binding.itemText.isSelected = item.state
        }
    }
}