package com.example.bailaappandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun CartScreen() {

    val monthlySpending = listOf(1200f, 1800f, 900f, 2200f, 1500f, 2000f)
    val cartItems = listOf(
        Outfit(1, "Красное платье", "4500 ₽", "", 1),
        Outfit(2, "Черные туфли", "3200 ₽", "", 1),
        Outfit(3, "Сумка", "2800 ₽", "", 2)
    )
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

@Composable
fun SpendingChart(data: List<Float>) {

    val maxValue = data.maxOrNull() ?: 0f

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

                val barHeight = (value / maxValue) * size.height

                drawRect(
                    color = barColor,
                    topLeft = Offset(
                        x = index * barWidth * 1.5f,
                        y = size.height - barHeight
                    ),
                    size = Size(barWidth, barHeight)
                )
            }
        }
    }
}

@Composable
fun CartItemRow(item: Outfit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = item.price,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ProfileXmlScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_profile, null, false)

            val etName = view.findViewById<EditText>(R.id.etName)
            val etEmail = view.findViewById<EditText>(R.id.etEmail)
            val btnEdit = view.findViewById<Button>(R.id.btnEdit)

            var isEditing = false

            etName.setText("Иван Иванов")
            etEmail.setText("ivan@email.com")

            btnEdit.setOnClickListener {
                isEditing = !isEditing
                etName.isEnabled = isEditing
                etEmail.isEnabled = isEditing
                btnEdit.text = if (isEditing) "Сохранить" else "Редактировать"
            }

            view
        }
    )
}