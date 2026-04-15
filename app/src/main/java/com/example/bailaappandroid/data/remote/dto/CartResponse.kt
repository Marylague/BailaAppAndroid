package com.example.bailaappandroid.data.remote.dto

import com.example.bailaappandroid.data.model.Outfit
import com.example.bailaappandroid.presentation.cart.CartBlock

data class CartResponse(
    val blocks: List<BDUIBlockDto> = emptyList()
)

data class BDUIBlockDto(
    val type: String,
    val data: Map<String, Any>? = null
)


fun BDUIBlockDto.toDomain(): CartBlock? {
    return when (type) {
        "spending_chart" -> {
            val values = (data?.get("points") as? List<*>)?.mapNotNull { (it as? Number)?.toFloat() }
            values?.let { CartBlock.Spending(it, "Ваши траты") }
        }

        "cart_items" -> {
            val rawItems = data?.get("items") as? List<Map<String, Any>>
            val items = rawItems?.map { map ->
                Outfit(
                    id = (map["id"] as? Number)?.toLong() ?: 0L,
                    name = map["name"]?.toString() ?: "Unknown",
                    price = map["price"]?.toString() ?: "0",
                    imageUrl = map["imageUrl"]?.toString() ?: "",
                    collectionId = (map["collectionId"] as? Number)?.toLong() ?: 0L
                )
            } ?: emptyList()

            CartBlock.Items(items, data?.get("title")?.toString() ?: "Товары в корзине")
        }

        "checkout_summary" -> {
            val total = data?.get("total")?.toString() ?: "0"
            CartBlock.Checkout(total, "Оформить")
        }
        else -> null
    }
}