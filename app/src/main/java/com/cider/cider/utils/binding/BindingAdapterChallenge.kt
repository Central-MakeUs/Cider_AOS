package com.cider.cider.utils.binding

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    view.text = if (day == null) "" else if (day == 0) "" else "D${day}"
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
fun setImageDrawableWithType(imageView: ImageView, challenge: Category) {
    when (challenge) {
        Category.INVESTING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.image_investing))
        }
        Category.MONEY_MANAGEMENT -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.image_money_management))
        }
        Category.SAVING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.image_saving))
        }
        Category.FINANCIAL_LEARNING -> {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.image_financial_learning))
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