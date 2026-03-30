package com.example.bailaappandroid.presentation.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bailaappandroid.presentation.cart.components.SpendingChart
import com.example.bailaappandroid.presentation.cart.components.CartItemRow


@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {

    val monthlySpending = listOf(1200f, 1800f, 900f, 2200f, 1500f, 2000f)
    val cartItems = viewModel.cartItems

    val totalPrice = 4500 + 3200 + 2800

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Ваши траты",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        SpendingChart(data = monthlySpending)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Товары в корзине",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(cartItems) { item ->
                CartItemRow(item)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Итого: $totalPrice ₽",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Заказать")
        }
    }
}
