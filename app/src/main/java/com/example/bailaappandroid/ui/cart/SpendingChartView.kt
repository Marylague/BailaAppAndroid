package com.example.bailaappandroid.ui.cart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SpendingChartView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    var data: List<Float> = listOf(1200f, 1800f, 900f, 2200f)

    private val paint = Paint().apply {
        color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val max = data.maxOrNull() ?: 0f
        val barWidth = width / (data.size * 1.5f)

        data.forEachIndexed { index, value ->
            val barHeight = (value / max) * height

            canvas.drawRect(
                index * barWidth * 1.5f,
                height - barHeight,
                index * barWidth * 1.5f + barWidth,
                height.toFloat(),
                paint
            )
        }
    }
}