package com.cider.cider.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val spacingLeft: Int,
                            private val spacingTop: Int,
                            private val spacingRight: Int,
                            private val spacingBottom: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(spacingLeft, spacingTop, spacingRight, spacingBottom)
    }
}