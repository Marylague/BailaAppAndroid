package com.example.bailaappandroid.presentation.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bailaappandroid.data.model.Outfit
import com.example.bailaappandroid.presentation.designsystem.components.PriceLabel
import com.example.bailaappandroid.presentation.designsystem.components.PrimaryButton


@Composable
fun SpendingBlock(monthlySpending: List<Float>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Ваши траты",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        SpendingChart(data = monthlySpending)
    }
}

@Composable
fun CartItemsBlock(cartItems: List<Outfit>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Товары в корзине",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(12.dp))

        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(cartItems) { item ->
                CartItemRow(item)
            }
        }
    }
}

@Composable
fun CheckoutBlock(totalPrice: String, onCheckoutClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        PriceLabel(price = totalPrice, prefix = "Итого")

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = "Оформить заказ",
            onClick = onCheckoutClick
        )
    }
}