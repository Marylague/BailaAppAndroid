package com.example.bailaappandroid.presentation.cart

import com.example.bailaappandroid.data.model.Outfit

sealed class CartBlock {
    data class Spending(val data: List<Float>, val title: String) : CartBlock()
    data class Items(val list: List<Outfit>, val title: String) : CartBlock()
    data class Checkout(val total: String, val buttonText: String) : CartBlock()
}