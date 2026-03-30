package com.example.bailaappandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.bailaappandroid.presentation.cart.CartScreen
import com.example.bailaappandroid.presentation.catalog.CatalogScreen
import com.example.bailaappandroid.presentation.user.ProfileXmlScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationItem.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavigationItem.Home.route) {
            CatalogScreen()
        }

        composable(NavigationItem.Cart.route) {
            CartScreen()
        }

        composable(NavigationItem.Profile.route) {
            ProfileXmlScreen()
        }
    }
}