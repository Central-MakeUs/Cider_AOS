package com.cider.cider.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.databinding.ItemCertifyBinding
import com.cider.cider.databinding.ItemCertifyInDetailBinding
import com.cider.cider.databinding.ItemFeedBinding
import com.cider.cider.domain.model.CertifyDetailModel
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.presentation.viewmodel.CertifyViewModel
import com.cider.cider.presentation.viewmodel.ChallengeDetailViewModel
import com.cider.cider.utils.ItemDiffCallback

class CertifyDetailAdapter(
    private val vm: ChallengeDetailViewModel
    ): ListAdapter<CertifyDetailModel, RecyclerView.ViewHolder>(
    ItemDiffCallback<CertifyDetailModel>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id }
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CertifyVieHolder(ItemCertifyInDetailBinding.inflate(inflater, parent, false))
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
        private val binding: ItemCertifyInDetailBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CertifyDetailModel) {
            binding.certify = item
            binding.vm = vm
            binding.executePendingBindings()

            binding.ivImage.setOnClickListener {
                binding.ivImage.let {
                    if (it.scaleType == ImageView.ScaleType.CENTER_CROP) {
                        it.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    } else {
                        it.scaleType = ImageView.ScaleType.CENTER_CROP
                    }
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

    private var listener: ChallengeCardAdapter.OnItemClickListener? = null

    fun setOnItemClickListener(listener: ChallengeCardAdapter.OnItemClickListener) {
        this.listener = listener
    }
}