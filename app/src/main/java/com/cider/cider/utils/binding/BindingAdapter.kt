package com.cider.cider.utils.binding

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:layout_height")
fun setLayoutHeight(view: View, height: Int) {
    view.layoutParams = view.layoutParams.apply {
        this.height = if (height == 0 ) {
            LayoutParams.WRAP_CONTENT
        } else {
            0
        }
    }
}