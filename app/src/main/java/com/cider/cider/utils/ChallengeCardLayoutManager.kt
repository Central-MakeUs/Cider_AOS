package com.cider.cider.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

class ChallengeCardLayoutManager(context: Context): LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {

    override fun getDecoratedLeft(child: View): Int {
        val position = getPosition(child)
        return if (position == 0) {
            val marginDp = 24 // 첫 번째 아이템의 왼쪽 마진(dp)
            val marginPx = (marginDp * child.context.resources.displayMetrics.density).toInt()
            super.getDecoratedLeft(child) + marginPx
        } else {
            super.getDecoratedLeft(child)
        }
    }

    override fun layoutDecoratedWithMargins(
        child: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        val position = getPosition(child)
        if (position != 0) {
            super.layoutDecoratedWithMargins(child, left, top, right, bottom)
        }
    }
}