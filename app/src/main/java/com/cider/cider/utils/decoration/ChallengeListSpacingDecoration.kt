package com.cider.cider.utils.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cider.cider.R

class ChallengeListSpacingDecoration(private val context: Context, private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        outRect.bottom = context.resources.getDimensionPixelSize(R.dimen.challenge_card_bottom)
        // 첫 번째 아이템에 대해 왼쪽 여백을 추가
        if (position%2 == 0) {
            outRect.left = context.resources.getDimensionPixelSize(R.dimen.challenge_card_start)
            outRect.right = spacing
        } else {
            outRect.right = context.resources.getDimensionPixelSize(R.dimen.challenge_card_start)
        }
    }
}