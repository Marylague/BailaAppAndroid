package com.example.bailaappandroid.presentation.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PriceLabel(
    price: String,
    prefix: String = "",
    modifier: Modifier = Modifier
) {
    Text(
        text = if (prefix.isEmpty()) "$price ₽" else "$prefix: $price ₽",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}