package com.example.bailaappandroid.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bailaappandroid.R
import com.example.bailaappandroid.data.Outfit

class CartFragment : Fragment(R.layout.fragment_cart) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerCart = view.findViewById<RecyclerView>(R.id.recyclerCart)
        val chartView = view.findViewById<SpendingChartView>(R.id.chartView)

        val cartItems = listOf(
            Outfit(1, "Красное платье", "4500 ₽", "", 1),
            Outfit(2, "Черные туфли", "3200 ₽", "", 1),
            Outfit(3, "Сумка", "2800 ₽", "", 2)
        )

        recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        recyclerCart.adapter = CartAdapter(cartItems)

        chartView.data = listOf(1200f, 1800f, 900f, 2200f)
        chartView.invalidate()
    }
}