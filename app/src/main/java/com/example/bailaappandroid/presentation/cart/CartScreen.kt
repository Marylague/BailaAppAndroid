package com.example.bailaappandroid.presentation.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bailaappandroid.presentation.cart.components.CartItemsBlock
import com.example.bailaappandroid.presentation.cart.components.CheckoutBlock
import com.example.bailaappandroid.presentation.cart.components.SpendingBlock

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
        SpendingBlock(monthlySpending = monthlySpending)

        Spacer(modifier = Modifier.height(24.dp))

        CartItemsBlock(
            cartItems = cartItems,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        CheckoutBlock(
            totalPrice = totalPrice.toString(),
            onCheckoutClick = { }
        )
    }
}
