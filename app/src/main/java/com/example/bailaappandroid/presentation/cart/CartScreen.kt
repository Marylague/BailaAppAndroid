package com.example.bailaappandroid.presentation.cart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bailaappandroid.presentation.cart.components.CartItemRow
import com.example.bailaappandroid.presentation.cart.components.CheckoutBlock
import com.example.bailaappandroid.presentation.cart.components.SpendingBlock

@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {
    val blocks = viewModel.uiState

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        blocks.forEach { block ->
            when (block) {
                is CartBlock.Spending -> {
                    item(key = "spending") {
                        SpendingBlock(monthlySpending = block.data)
                    }
                }

                is CartBlock.Items -> {
                    item(key = "items_header") {
                        Text(
                            text = block.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(block.list) { item ->
                        CartItemRow(item)
                    }
                }

                is CartBlock.Checkout -> {
                    item(key = "checkout") {
                        CheckoutBlock(
                            totalPrice = block.total,
                            onCheckoutClick = {  }
                        )
                    }
                }
            }
        }
    }
}