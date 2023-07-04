package com.cider.cider.utils.binding

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cider.cider.domain.type.Birth
import java.sql.Date


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
    view.text = "${date.year}년 ${date.month+1}월 ${date.day}일"
}