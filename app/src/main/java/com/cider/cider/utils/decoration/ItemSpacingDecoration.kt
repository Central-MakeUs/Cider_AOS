package com.cider.cider.utils.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.R

class ItemSpacingDecoration(private val context: Context, private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        // 첫 번째 아이템에 대해 왼쪽 여백을 추가
        if (position == 0) {
            outRect.left = context.resources.getDimensionPixelSize(R.dimen.challenge_card_start)
        }

        // 마지막 아이템에 대해 오른쪽 여백을 추가
        if (position == itemCount - 1) {
            outRect.right = context.resources.getDimensionPixelSize(R.dimen.challenge_card_start)
        } else {
            outRect.right = spacing
        }
    }
}