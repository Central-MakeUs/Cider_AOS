package com.cider.cider.utils.binding

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cider.cider.domain.type.Birth


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

@SuppressLint("SetTextI18n")
@BindingAdapter("textDate")
fun setTextDate(view: TextView, date: Birth) {
    view.text = "${(date.year).toString().padStart(4,'0')}년 " +
            "${(date.month+1).toString().padStart(2,'0')}월 " +
            "${(date.day).toString().padStart(2,'0')}일"
}

@BindingAdapter("select")
fun setSelect(view: View, isSelected: Boolean) {
    view.isSelected = isSelected
}