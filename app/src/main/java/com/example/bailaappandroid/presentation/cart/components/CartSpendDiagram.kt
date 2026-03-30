package com.example.bailaappandroid.presentation.cart.components

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp


@Composable
fun SpendingChart(data: List<Float>) {
    val maxValue = data.maxOrNull() ?: 0f

    val barHeights = remember { mutableStateListOf<Float>().apply { repeat(data.size) { add(0f) } } }

    LaunchedEffect(Unit) {
        data.forEachIndexed { index, targetValue ->
            barHeights[index] = (targetValue / maxValue) * 300f
        }
    }

    val animatedHeights = barHeights.map { targetHeight ->
        animateFloatAsState(
            targetValue = targetHeight,
            animationSpec = tween(durationMillis = 1500)
        ).value
    }

    val barColor = MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val barWidth = size.width / (data.size * 1.5f)

            data.forEachIndexed { index, value ->
                val barHeight = animatedHeights[index]

                drawRect(
                    color = barColor,
                    topLeft = Offset(
                        x = index * barWidth * 1.5f,
                        y = size.height - barHeight
                    ),
                    size = Size(barWidth, barHeight)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "$value ₽",
                    index * barWidth * 1.5f + barWidth / 2,
                    size.height - barHeight - 10,
                    Paint().apply {
                        color = Color.BLACK
                        textSize = 32f
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}
