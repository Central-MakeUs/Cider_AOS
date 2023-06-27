package com.cider.cider.utils.binding

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.databinding.BindingAdapter


@BindingAdapter("layout_height")
fun setLayoutHeight(view: View, height: Int) {
    val layoutParams: LayoutParams = view.layoutParams
    layoutParams.height = if (height == 0) {
        LayoutParams.WRAP_CONTENT
    } else {
        0
    }
    view.layoutParams = layoutParams
}