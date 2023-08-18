package com.cider.cider.utils.binding

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cider.cider.R
import com.cider.cider.domain.type.ReviewType
import com.cider.cider.domain.type.challenge.Category
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
    view.text = if (day == null) "" else if (day == 0) "" else if (day > 0) "D+${day}" else "D${day}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("infoPeople","participation", requireAll = true)
fun setTextInfoPeople(view: TextView, people: Int, participation: ParticipationStatus,) {
    view.text = "${people}명 ${participation.text}"
}

@BindingAdapter("challenge")
fun setChallengeText(view: TextView, challenge: Category) {
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

@SuppressLint("SetTextI18n")
@BindingAdapter("countText", "maxCount", requireAll = true)
fun setTextCount(view: TextView, text: String, max: Int) {
    view.text = "${text.length}/${max}"
}

@BindingAdapter("imageUri")
fun setImageUri(imageView: ImageView, imageUri: Uri?) {
    if (imageUri != null) {
        Glide.with(imageView)
            .load(imageUri)
            .apply(RequestOptions().transform(RoundedCorners(((4f) * Resources.getSystem().displayMetrics.density).toInt())))
            .into(imageView)
    }
}

@BindingAdapter("imageDrawable")
fun setImageDrawable(imageView: ImageView, drawable: Drawable?) {
    if (drawable != null) {
        imageView.setImageDrawable(drawable)
    }
}

@BindingAdapter("imageDrawableWithType")
fun setImageDrawableWithType(imageView: ImageView, challenge: Category?) {
    when (challenge) {
        Category.INVESTING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.contents_investing))
        }
        Category.MONEY_MANAGEMENT -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.contents_money_management))
        }
        Category.SAVING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.contents_saving))
        }
        Category.FINANCIAL_LEARNING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.contents_financial_learning))
        }
        else -> {

        }
    }
}

@BindingAdapter("iconDrawableWithType")
fun setIconDrawableWithType(imageView: ImageView, challenge: Category?) {
    when (challenge) {
        Category.INVESTING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.home_challengedetail))
        }
        Category.MONEY_MANAGEMENT -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.graph_challengedetail))
        }
        Category.SAVING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.pig_challengedetail))
        }
        Category.FINANCIAL_LEARNING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.card_challengedetail))
        }
        else -> {

        }
    }
}

@BindingAdapter("textColorChallenge")
fun setTextColorChallenge(view: TextView, challenge: Category) {
    when (challenge) {
        Category.INVESTING -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.btn_mint))
        }
        Category.MONEY_MANAGEMENT -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.btn_blue))
        }
        Category.SAVING -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.btn_pink))
        }
        Category.FINANCIAL_LEARNING -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.btn_purple))
        }
    }
}

@BindingAdapter("tapState", "tapCurrent", requireAll = true)
fun setTapResource(view: TextView, tapState: ReviewType, tapCurrent: ReviewType) {
    when (tapCurrent) {
        ReviewType.REVIEW -> {
            when (tapState) {
                ReviewType.REVIEW -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_bold)
                    view.setBackgroundResource(R.drawable.shape_tap_active)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
                }
                ReviewType.REJECTED, ReviewType.FAILED -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_regular)
                    view.setBackgroundResource(R.drawable.shape_tap_disable)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.gray_5))
                }
                ReviewType.APPROVED -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_regular)
                    view.setBackgroundResource(R.drawable.shape_tap_disable)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.gray_5))
                }
            }
        }
        ReviewType.REJECTED, ReviewType.FAILED -> {
            when (tapState) {
                ReviewType.REVIEW -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_regular)
                    view.setBackgroundResource(R.drawable.shape_tap_complete)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
                }
                ReviewType.REJECTED, ReviewType.FAILED -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_bold)
                    view.setBackgroundResource(R.drawable.shape_tap_active)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
                }
                ReviewType.APPROVED -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_regular)
                    view.setBackgroundResource(R.drawable.shape_tap_disable)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.gray_5))
                }
            }
        }
        ReviewType.APPROVED -> {
            when (tapState) {
                ReviewType.REVIEW -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_regular)
                    view.setBackgroundResource(R.drawable.shape_tap_complete)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
                }
                ReviewType.REJECTED, ReviewType.FAILED -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_regular)
                    view.setBackgroundResource(R.drawable.shape_tap_complete)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
                }
                ReviewType.APPROVED -> {
                    view.typeface = ResourcesCompat.getFont(view.context, R.font.pretendard_bold)
                    view.setBackgroundResource(R.drawable.shape_tap_active)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
                }
            }
        }
    }
}

@BindingAdapter("percent_position")
fun setPercentPosition(view: View, bias: Int) {
    val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
    if (bias <= 0) {
        layoutParams.horizontalBias = 0.0f
    } else {
        layoutParams.horizontalBias = bias/100.0f
    }
    view.layoutParams = layoutParams
}

@BindingAdapter("textExpand")
fun setTextExpand(view: TextView, isExpand: Boolean) {
    if (isExpand) {
        view.text = "접기"
    } else {
        view.text = "자세히보기"
    }
}

@BindingAdapter("textExpand2")
fun setTextExpand2(view: TextView, isExpand: Boolean) {
    if (isExpand) {
        view.maxLines = Int.MAX_VALUE
    } else {
        view.maxLines = 2
    }
}

@BindingAdapter("detailChallengeEnable")
fun setDetailChallengeEnable(view: View, detail: String?) {
    if (detail == "오늘 참여 인증하기" ) {
        view.isEnabled = true
        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#009DFF"))
    } else if (detail == "이 챌린지 참여하기" ) {
        view.isEnabled = true
        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#009DFF"))
    } else if (detail?.matches(Regex("챌린지 기다리기 D-\\d+")) == true) {
        view.isEnabled = true
        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#009DFF"))
    } else {
        view.isEnabled = false
        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#C1C6CA"))
    }
}