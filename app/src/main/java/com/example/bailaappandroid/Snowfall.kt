package com.example.bailaappandroid

import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import kotlin.random.Random

@Composable
fun SnowfallAnimation() {
    val snowflakes = remember {
        List(40) {
            Snowflake(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 6f + 4f,
                speed = Random.nextFloat() * 2f + 1f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition()

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing)
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        snowflakes.forEach { snowflake ->
            val y = (snowflake.y * height + offsetY * height * snowflake.speed) % height
            val x = snowflake.x * width

            drawCircle(
                color = Color.Blue.copy(alpha = 0.8f),
                radius = snowflake.size,
                center = Offset(x, y)
            )
        }
    }
}

data class Snowflake(
    val x: Float,
    val y: Float,
    val size: Float,
    val speed: Float
)