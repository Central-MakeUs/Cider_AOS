package com.cider.cider.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemFeedBinding
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.utils.ItemDiffCallback

class FeedAdapter(
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
            binding.executePendingBindings()

            binding.tvMoreText.setOnClickListener {
                if (binding.tvMoreText.text == "자세히 보기") {
                    binding.tvMoreText.text = "접기"
                    binding.tvFeedContent.maxLines = Int.MAX_VALUE
                } else {
                    binding.tvMoreText.text = "자세히 보기"
                    binding.tvFeedContent.maxLines = 2
                }
            }

/*            val feedImageAdapter = FeedImageAdapter()
            binding.vpImage.adapter = feedImageAdapter
            if (item.imageList != null) {
                feedImageAdapter.submitList(item.imageList)
            }

            val current = 1
            val total = feedImageAdapter.itemCount

            val indicator = "$current / $total"

            binding.tvPageIndicator.text = indicator

            binding.vpImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val currentPage = position + 1
                    val totalPages = feedImageAdapter.itemCount

                    val indicatorText = "$currentPage / $totalPages"
                    binding.tvPageIndicator.text = indicatorText
                }
            })

            binding.vpImage.apply {
                offscreenPageLimit = 4
                setPageTransformer(ImageSliderTransformer(4))
            }*/

        }
    }
}