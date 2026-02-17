package com.example.bailaappandroid

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : NavigationItem("home", Icons.Default.Home, "Главная")
    object Cart : NavigationItem("cart", Icons.Default.ShoppingCart, "Корзина")
    object Profile : NavigationItem("profile", Icons.Default.Person, "Профиль")
}