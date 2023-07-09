package com.cider.cider.utils.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.domain.type.challenge.ParticipationStatus


@BindingAdapter("select")
fun setSelect(imageView: ImageView, isSelected: Boolean) {
    imageView.isSelected = isSelected
}

@BindingAdapter("visible")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleRank")
fun setVisibilityRank(view: TextView, rank: Int?) {
    view.visibility = if (rank != null) View.VISIBLE else View.GONE
    view.text = if (rank != null) "${rank}위" else ""
}

@BindingAdapter("visibleDay")
fun setVisibilityDDay(view: TextView, day: Int?) {
    view.visibility = if (day != null) View.VISIBLE else View.GONE
    view.text = if (day != null) "D-${day}" else ""
}

@SuppressLint("SetTextI18n")
@BindingAdapter("infoPeople","participation", requireAll = true)
fun setTextInfoPeople(view: TextView, people: Int, participation: ParticipationStatus,) {
    view.text = "${people}명 ${participation.text}"
}

@BindingAdapter("challenge")
fun setChallengeText(view: TextView, challenge: Challenge) {
    view.text = challenge.text
}

@BindingAdapter("visibleStatus")
fun setParticipationStatus(view: View, status: ParticipationStatus) {
    if (status == ParticipationStatus.RECRUITING) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("duration")
fun setDuration(view: TextView, duration: Int) {
    view.text = "${duration}주"
}