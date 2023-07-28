package com.cider.cider.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomProgressBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val progressBarColor = Color.BLUE
    private val textColor = Color.BLACK
    private val textSize = 40f

    private var progress = 0f

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = this@CustomProgressBarView.textSize
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val barWidth = width * progress
        paint.color = progressBarColor
        canvas.drawRect(0f, 0f, barWidth, height.toFloat(), paint)

        paint.color = textColor
        canvas.drawText(progress.toString(), width / 2f, height / 2f, paint)
    }
}