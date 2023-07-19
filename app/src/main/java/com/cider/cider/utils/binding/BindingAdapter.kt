package com.cider.cider.utils.binding

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.cider.cider.R
import com.cider.cider.domain.type.Birth
import com.cider.cider.domain.type.challenge.Challenge


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

@BindingAdapter("selectTab")
fun setSelectTab(view: View, challenge: Challenge) {

}


@BindingAdapter("categoryTextView")
fun setCategoryTextView(view: TextView, challenge: Challenge) {
    view.text = challenge.comment
    val color = ContextCompat.getColor(view.context, challenge.colorResId)
    view.backgroundTintList = ColorStateList.valueOf(color)
}


@BindingAdapter("fontFamilyTab")
fun setFontFamilySelector(view: TextView, isSelected: Boolean) {
    if (isSelected) {
        view.typeface = ResourcesCompat.getFont(view.context, if (isSelected) R.font.pretendard_bold else R.font.pretendard_regular)
    }
}
