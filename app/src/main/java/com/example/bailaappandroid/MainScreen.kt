package com.example.bailaappandroid

// Файл: MainScreen.kt (или в MainActivity.kt)

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // 1. Создаем контроллер навигации
    val navController = rememberNavController()

    Scaffold(
        // 2. Указываем, что будет в нижней панели
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val items = listOf(NavigationItem.Home, NavigationItem.Cart, NavigationItem.Profile)

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        // Проверяем, активен ли этот элемент
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Этот код позволяет избежать накопления экранов в стеке
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Home.route) { HomeScreen() }
            composable(NavigationItem.Cart.route) { CartScreen() }
            composable(NavigationItem.Profile.route) { ProfileScreen() }
        }
    }
}
